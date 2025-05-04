package tech.hljzj.activiti.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tech.hljzj.activiti.config.Loader;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.listener.AssignMonitor;
import tech.hljzj.activiti.listener.TaskCompleteMonitor;
import tech.hljzj.activiti.pojo.FormInfo;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.activiti.pojo.TaskProgress;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.instance.TaskInfo;
import tech.hljzj.activiti.pojo.node.*;
import tech.hljzj.activiti.register.IProcess;
import tech.hljzj.activiti.util.InjectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public abstract class SimpleProcessRegister implements IProcess {
    public static final String VAR_PROCESS_MODEL_KEY = "_processModelKey";
    /**
     * 单级别撤回
     */
    public static final String VAR_PREV_TASK = "_prevTaskID";
    public static final String VAR_IS_ROLLBACK = "_isRollback";
    public static final String VAR_PREV_TASK_STACK = "_prevTaskIDStack";
    public static final String VAR_DEPLOY_ID = "_processDeployID";
    public static final String VAR_PASS_COUNT = "_passCount";
    public static final String VAR_DENY_COUNT = "_denyCount";
    public static final String VAR_PASS_USER = "_passUser_";
    public static final String VAR_DENY_USER = "_denyUser_";
    public static final String DYNAMIC_NODE_MODE = "_dynamicNodeMode";
    @Autowired
    @Lazy
    protected Registry registry;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected SpringProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 发起流程
     *
     * @param id        流程ID
     * @param name      流程实例名称
     * @param user      发起人
     * @param variables 流程表单变量
     * @return 流程实例
     */
    public ProcessInstance startProcess(String id, String name, User user, Map<String, Object> variables) {
        String key = processCode();
        PlatformTransactionManager transactionManager = processEngineConfiguration.getTransactionManager();
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_COMMITTED);
        //读已提交事务
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            if (variables == null) {
                variables = Collections.emptyMap();
            }
            Map<String, Object> variable = new LinkedHashMap<>(variables);
            //注入默认变量
            InjectUtil.setProcessInitiatorUser(variable, user);
            Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentKey(key)
                .latest().singleResult();
            variable.put(VAR_PROCESS_MODEL_KEY, key);
            ProcessInstance start = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(key)
                .name(name)
                .variables(variable)
                .variable(VAR_DEPLOY_ID, deployment.getId())
                .businessKey(id)
                .start();
            transactionManager.commit(transaction);
            return start;
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }
    }

    public List<Task> getAllTask() {
        return taskService.createTaskQuery().processDefinitionKey(processCode()).orderByTaskCreateTime().desc().list();
    }

    /**
     * 获取我的任务
     *
     * @param user 用户
     * @return 当前任务列表
     */
    public List<TaskInfo> getMyTask(User user) {
        return getMyTaskQuery(user).orderByTaskCreateTime().desc().list().stream().map(f -> {
            TaskInfo ti = new TaskInfo();
            ti.setId(f.getId());
            ti.setKey(f.getTaskDefinitionKey());
            ti.setName(f.getName());
            ti.setAssign(f.getAssignee());
            ti.setProcessId(f.getProcessInstanceId());
            ti.setInstance(f);
            return ti;
        }).collect(Collectors.toList());
    }

    /**
     * 获取我的任务
     *
     * @param user 用户
     * @return 当前任务列表
     */
    public TaskQuery getMyTaskQuery(User user) {
        List<String> assignIn = new ArrayList<>();
        if (user.getId() != null) {
            assignIn.add("user:" + user.getId());
        }

        if (user.getRoleId() != null) {
            assignIn.add("role:" + user.getRoleId());
        }

        return taskService.createTaskQuery()
            .processDefinitionKey(processCode())
            .taskAssigneeIds(assignIn);
    }

    public List<TaskInfo> getTaskDetail(List<TaskInfo> task, LoadOption options) {
        Map<String, ProcessModel> modelMap = new HashMap<>(1);
        for (TaskInfo taskInfo : task) {
            if (options.isPullFormInfo() || options.isPullProgress()) {
                String deployId = taskService.getVariable(taskInfo.getId(), VAR_DEPLOY_ID, String.class);
                if (!modelMap.containsKey(deployId)) {
                    try {
                        ProcessModel processModel = getModel(deployId);
                        modelMap.put(deployId, processModel);
                    } catch (IOException e) {
                        throw new ActException("模型解析失败!", e);
                    }
                }
                ProcessModel process = modelMap.get(deployId);
                Node node = getTaskDefine(taskInfo.getInstance().getTaskDefinitionKey(), process);

                if (options.isPullFormInfo()) {
                    FormInfo form = getForm(node);
                    taskInfo.setFormInfo(form);
                }
                if (options.isPullProgress()) {
                    //是否多人审批
                    if (node instanceof ApprovalNode) {
                        Object variable = taskService.getVariable(taskInfo.getId(), node.getId() + "Collection");
                        //所有审批人列表
                        //noinspection unchecked
                        List<String> users = variable instanceof List<?> ? (List<String>) variable : null;
                        taskInfo.setAllAssign(ObjUtil.defaultIfNull(users, Collections.emptyList()));
                        taskInfo.setMultipleApproval(taskInfo.getAllAssign().size() > 0);
                    }
                    Map<String, Object> variableAll = taskInfo.getInstance().getProcessVariables();
                    TaskProgress progress = TaskCompleteMonitor.getTaskProgress(variableAll);
                    taskInfo.setProgress(progress);
                }
            }
        }
        return task;
    }

    /**
     * 获取任务关联的表单信息
     *
     * @param taskId 任务ID
     * @return 表单信息
     */
    public FormInfo getForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return null;
        }
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String deployId = runtimeService.getVariable(task.getExecutionId(), VAR_DEPLOY_ID, String.class);
        try {
            Node taskDefine = getTaskDefine(taskDefinitionKey, deployId);
            return getForm(taskDefine);
        } catch (IOException e) {
            throw new ActException("模型解析失败!", e);
        }
    }

    public FormInfo getForm(Node taskDefine) {
        FormInfo formInfo = new FormInfo();
        List<ProcessProp> ps = new ArrayList<>();
        formInfo.setProps(ps);

        if (taskDefine instanceof ApprovalNode) {
            formInfo.setOperations(((ApprovalNode) taskDefine).getOperations());

            List<FormProperty> formProperties = ((ApprovalNode) taskDefine).getFormProperties();
            Map<String, FormProperty> form = new LinkedHashMap<>();
            for (FormProperty formProperty : formProperties) {
                form.put(formProperty.getId(), formProperty);
            }
            List<ProcessProp> props = props();

            for (ProcessProp prop : props) {
                ProcessProp itemProp = BeanUtil.copyProperties(prop, ProcessProp.class);

                FormProperty formProperty = form.get(itemProp.getKey());
                itemProp.setReadonly(ObjUtil.defaultIfNull(formProperty.getReadonly(), false));
                itemProp.setVisible(!ObjUtil.defaultIfNull(formProperty.getHidden(), false));
                itemProp.setRequired(!ObjUtil.defaultIfNull(formProperty.getRequired(), false));
                ps.add(itemProp);
            }
        }
        return formInfo;
    }

    /**
     * 审批通过
     *
     * @param taskId 任务ID
     * @param user   用户
     */
    public void passTask(String taskId, User user, Map<String, Object> variables) {
        variables = ObjUtil.defaultIfNull(variables, new HashMap<>());
        variables.put(VAR_PASS_USER + user.getId(), 1);
        operationTask(taskId, user, variables);
    }

    /**
     * 审批拒绝
     *
     * @param taskId 任务ID
     * @param user   用户
     */
    public void denyTask(String taskId, User user, Map<String, Object> variables) {
        variables = ObjUtil.defaultIfNull(variables, new HashMap<>());
        variables.put(VAR_DENY_USER + user.getId(), 1);
        operationTask(taskId, user, variables);
    }

    private void operationTask(String taskId, User user, Map<String, Object> mergeVariable) {
        operationTask(() -> {
            Task task = getMyTaskQuery(user).taskId(taskId).singleResult();
            if (task == null) {
                throw new ActException("当前人员无法完成指定的任务，如需更换人员，请对任务进行转办");
            }
            if (StrUtil.isBlankIfStr(user.getId())) {
                throw new ActException("完成任务时必须指定具体人员");
            }
            task.setAssignee("user:" + user.getId());
            //如果使用setVariables方式，只会创建不存在的变量，不会更改
            mergeVariable.forEach((k, v) -> taskService.setVariable(taskId, k, v));
//            taskService.setVariables(task.getProcessInstanceId(), mergeVariable);
            taskService.complete(taskId);
        });
    }

    private void operationTask(Runnable runnable) {
        PlatformTransactionManager transactionManager = processEngineConfiguration.getTransactionManager();
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            runnable.run();
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
        }
    }

    private <R> R operationTask(Callable<R> runnable) {
        PlatformTransactionManager transactionManager = processEngineConfiguration.getTransactionManager();
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            R r = runnable.call();
            transactionManager.commit(transaction);
            return r;
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new ActException("操作异常", e);
        }
    }

    /**
     * 退回
     *
     * @param taskId 任务id
     * @param user   操作用户
     * @return 退回是否成功
     */
    public boolean sendBack(String taskId, User user) {
        return operationTask(() -> {
            TaskQuery taskQuery = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssigneeIds(Arrays.asList(
                    "user:" + user.getId(),
                    "role:" + user.getRoleId()
                ));
            Task task = taskQuery.singleResult();
            if (task == null) {
                return false;
            }
            //退回操作
            String processInstanceId = task.getProcessInstanceId();
            runtimeService.deleteProcessInstance(processInstanceId, "true");
            return true;
        });
    }

    /**
     * 审批转办
     *
     * @param taskId 任务ID
     * @param to     转办人
     */
    public void turnToDo(String taskId, User to) {
        if (to.getId() == null) {
            throw new ActException("转办人不允许为空!");
        }
        operationTask(() -> taskService.setAssignee(taskId, "user:" + to.getId().toString()));
    }

    /**
     * 撤回
     * <p>
     * 单级撤回，该撤回动作可以在发起之后，下一任务完成前执行
     * 此撤回动作只能执行一次，执行后需要再次提交之后才能再次执行，且如果该环节前仍有任务，之前的任务无法直接执行退回动作
     *
     * @param processId 流程ID
     * @param user      退回人
     */
    public void rollback(String processId, User user) {
        List<Task> procList = taskService.createTaskQuery().processInstanceBusinessKey(processId).listPage(0, 1);
        if (procList.size() == 0) {
            throw new ActException("未找到可退回的节点");
        }
        Task proc = procList.get(0);
        String variable = taskService.getVariable(proc.getId(), VAR_PREV_TASK, String.class);
        if (StrUtil.isBlank(variable)) {
            throw new ActException("未找到可退回的节点");
        }
        goTask(proc.getProcessInstanceId(), variable);
    }

    /**
     * 跳转
     *
     * @param processId 流程ID
     * @param toTaskId  需要跳转到的位置
     */
    public void goTask(String processId, String toTaskId) {
        //让审批回到上一步骤，可以通过变量获取上一步骤标识，然后清空当前的任务列表，重新进行指向即可
        operationTask(() -> {
            List<Task> activeTaskList = taskService.createTaskQuery().processInstanceBusinessKey(processId).list();
            Task lastTask = activeTaskList.get(0);
            if (StrUtil.isBlank(toTaskId)) {
                //kill process
                runtimeService.deleteProcessInstance(lastTask.getProcessInstanceId(), "true");
                return;
            }

            String executionId = lastTask.getExecutionId();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(lastTask.getProcessDefinitionId());

            Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(execution.getActivityId());
            List<SequenceFlow> outgoingFlows = flowNode.getOutgoingFlows();

            List<SequenceFlow> outFlowBack = new ArrayList<>(outgoingFlows);
            //清空对外的流
            outgoingFlows.clear();

            Map<String, Object> localVariable = new HashMap<>(1);
            localVariable.put(VAR_IS_ROLLBACK, true);
            //完成以下任务
            for (int i = 1; i < activeTaskList.size(); i++) {
                taskService.complete(activeTaskList.get(i).getId(), localVariable, true);
            }
            FlowElement currTaskNode = bpmnModel.getFlowElement(lastTask.getTaskDefinitionKey());
            FlowElement toTaskNode = bpmnModel.getFlowElement(toTaskId);
            SequenceFlow sequenceFlow = new SequenceFlow();
            sequenceFlow.setId("_tmp");
            sequenceFlow.setSourceRef(currTaskNode.getId());
            sequenceFlow.setSourceFlowElement(currTaskNode);

            sequenceFlow.setTargetRef(toTaskNode.getId());
            sequenceFlow.setTargetFlowElement(toTaskNode);

            sequenceFlow.setExecutionListeners(Collections.singletonList(AssignMonitor.defaultListener()));
            outgoingFlows.add(sequenceFlow);
            //标识当前任务是产生了退回
            taskService.complete(lastTask.getId(), localVariable, true);
            //删除正向节点
            outgoingFlows.clear();

            //恢复原始路径
            outgoingFlows.addAll(outFlowBack);
        });
    }

    public ProcessModel getModel() throws JsonProcessingException {
        Model model = repositoryService.createModelQuery().modelKey(processCode()).singleResult();
        byte[] metaInfo = repositoryService.getModelEditorSourceExtra(model.getId());

        return objectMapper.readValue(new String(metaInfo, StandardCharsets.UTF_8), Loader.class).getModelBase();
    }

    public ApprovalNode getNextTaskDefine(String currentTaskDefineId) throws JsonProcessingException {
        return getNextTaskDefine(currentTaskDefineId, getModel());
    }

    public ApprovalNode getNextTaskDefine(String currentTaskDefineId, ProcessModel processModel) {
        Node currentNodeDefine = findTaskNode(currentTaskDefineId, processModel.getProcess());
        if (currentNodeDefine == null) {
            return null;
        }
        //如果这个流程是网关
        if (currentNodeDefine instanceof ExclusiveNode) {
            ExclusiveNode en = (ExclusiveNode) currentNodeDefine;
            for (ConditionNode child : en.getChildren()) {
                ApprovalNode approvalNode = findFirstUserTaskAfterCurrent(child);
                if (approvalNode != null) {
                    return approvalNode;
                }
            }
        }
        Node findRootNode = currentNodeDefine.getChild();
        if (findRootNode == null) {
            if (StrUtil.isNotBlank(currentNodeDefine.getBranchId())) {
                findRootNode = getTaskDefine(currentNodeDefine.getBranchId(), processModel);
            }
        }
        return findFirstUserTaskAfterCurrent(findRootNode);
    }

    public ProcessModel getModel(String deploymentId) throws IOException {
        String metaInfo;
        try (InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, processCode() + ".define.json")) {
            metaInfo = IoUtil.readUtf8(resourceAsStream);
        }
        return objectMapper.readValue(metaInfo, Loader.class).getModelBase();
    }

    public Node getTaskDefine(String currentTaskDefineId, ProcessModel processModel) {
        return findTaskNode(currentTaskDefineId, processModel.getProcess());
    }

    public Node getTaskDefine(String currentTaskDefineId, String deploymentId) throws IOException {
        return getTaskDefine(currentTaskDefineId, getModel(deploymentId));
    }

    private ApprovalNode findFirstUserTaskAfterCurrent(Node node) {
        if (node == null) {
            return null;
        }
        //如果是，直接返回
        if (node instanceof ApprovalNode) {
            return (ApprovalNode) node;
        } else if (node instanceof ExclusiveNode) {
            //如果是网关，分开检索
            for (ConditionNode conditionNode : ((ExclusiveNode) node).getChildren()) {
                ApprovalNode item = findFirstUserTaskAfterCurrent(conditionNode.getChild());
                if (item != null) {
                    return item;
                }
            }
        }
        return findFirstUserTaskAfterCurrent(node.getChild());
    }

    private Node findTaskNode(String id, Node node) {
        if (node == null) {
            return null;
        }
        if (id.equals(node.getId())) {
            return node;
        } else {
            if (node instanceof ExclusiveNode) {
                //如果是网关，分开检索
                for (ConditionNode conditionNode : ((ExclusiveNode) node).getChildren()) {
                    Node item = findTaskNode(id, conditionNode);
                    if (item != null) {
                        String branchId = Optional.ofNullable(node.getChild()).map(Node::getId).orElse(node.getBranchId());
                        item.setBranchId(branchId);
                        return item;
                    }
                }
            }
            return findTaskNode(id, node.getChild());
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class LoadOption {
        private boolean pullFormInfo = true;
        private boolean pullProgress = false;

        public static LoadOption create() {
            return new LoadOption();
        }


    }
}
