package tech.hljzj.activiti.listener;

import cn.hutool.core.util.ObjUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.TaskProgress;
import tech.hljzj.activiti.util.InjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.*;

public class TaskCompleteMonitor implements TaskListener {
    public static TaskProgress getTaskProgress(Map<String, Object> variables) {
        //因为任务完成了，此时需要生成一系列相关变量。
        List<String> passUser = new ArrayList<>();
        List<String> denyUser = new ArrayList<>();

        String passPrefix = VAR_PASS_USER;
        String denyPrefix = VAR_DENY_USER;

        for (String p : variables.keySet()) {
            if (p.startsWith(passPrefix)) {
                p = p.substring(passPrefix.length());
                passUser.add(p);
            } else if (p.startsWith(denyPrefix)) {
                p = p.substring(denyPrefix.length());
                denyUser.add(p);
            }
        }

        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setPassUser(passUser);
        taskProgress.setDenyUser(denyUser);
        return taskProgress;
    }


    private static void removeVariable(DelegateExecution execution, Map<String, Object> variables, List<String> keys) {
        DelegateExecution scope = execution;
        do {
            scope.removeVariables(keys);
            scope = scope.getParent();
        } while (scope != null);
        variables.remove(VAR_PREV_TASK);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        // 获取流程执行上下文
        DelegateExecution execution = delegateTask.getExecution();

        long completedInstances = execution.getParent().getVariableInstance("nrOfCompletedInstances").getLongValue();
        long allOfInstances = execution.getParent().getVariableInstance("nrOfInstances").getLongValue();

        String modelKey = execution.getVariable(VAR_PROCESS_MODEL_KEY, String.class);

        Registry registry = Context.getBean(Registry.class);
        SimpleProcessRegister processor = registry.get(modelKey);
        Map<String, Object> variables = delegateTask.getVariables();

        TaskProgress taskProgress = getTaskProgress(variables);

        String assignee = delegateTask.getAssignee();

        boolean rollback = ObjUtil.defaultIfNull(execution.getVariableLocal(VAR_IS_ROLLBACK, Boolean.class), false);

        //执行虽然完成了，但是数值尚未减少。因为递减的函数在监听器后执行
        String taskDefineKey = delegateTask.getTaskDefinitionKey();
        if (!rollback && allOfInstances - completedInstances > 1 && !earlyTermination(taskDefineKey, execution, taskProgress)) {
            //通知完成状态
            processor.onComplete(
                execution.getProcessInstanceBusinessKey(),
                delegateTask,
                InjectUtil.getProcessInitiatorUser(variables),
                assignee,
                taskProgress.getPassUser().contains(assignee),
                taskProgress.setComplete(false)
            );
            return;
        }


        removeVariable(execution, variables, taskProgress.getPassUser().stream().map(f -> VAR_PASS_USER + f).collect(Collectors.toList()));
        removeVariable(execution, variables, taskProgress.getDenyUser().stream().map(f -> VAR_DENY_USER + f).collect(Collectors.toList()));

        //任务实例开始时貌似会从root节点实例复制变量到当前实例，
        //而查找顺序时由近到远，会导致其增减时使用错误的基数，在此删除可以解决问题。
        execution.removeVariableLocal("nrOfInstances");
        execution.removeVariableLocal("nrOfActiveInstances");
        execution.removeVariableLocal("nrOfCompletedInstances");
        //瞬态的同意数量和拒绝数量
        execution.setTransientVariable(VAR_PASS_COUNT, taskProgress.getPassUser().size());
        execution.setTransientVariable(VAR_DENY_COUNT, taskProgress.getDenyUser().size());
        //如果是常规完成时，将变量设置在流程一级
        //这里未来可以支持无限级退回，但是没有必要，也不符合业务要求！
        execution.setTransientVariable(VAR_PREV_TASK, taskDefineKey);

        //通知完成状态
        processor.onComplete(
            execution.getProcessInstanceBusinessKey(),
            delegateTask,
            InjectUtil.getProcessInitiatorUser(variables),
            assignee,
            taskProgress.getPassUser().contains(assignee),
            taskProgress.setComplete(true).setRollback(rollback)
        );
    }

    /**
     * 提前终止
     * <p>
     * 判定当前节点是什么，是或签，还是会签
     * 如果是或签，则只要出现任意一个同意，则结束
     * 如果是会签，则只要出现任意一个不同意，则结束
     */
    private boolean earlyTermination(String nodeId, DelegateExecution execution, TaskProgress progress) {

        if (!execution.hasVariable(DYNAMIC_NODE_MODE + nodeId)) {
            return false;
        }


        boolean earlyTermination;
        DelegateExecution parent = execution.getParent();
        VariableInstance nrOfInstances = parent.getVariableInstance("nrOfInstances");

        int totalUser = nrOfInstances.getLongValue().intValue();
        int totalPassUser = progress.getPassUser().size();
        int totalDenyUser = progress.getDenyUser().size();

        String var = execution.getVariable(DYNAMIC_NODE_MODE + nodeId, String.class);
        // 百分比具有一定的不确定性，因为随时可能出现加签情况
        // 但是如果百分比的值是100，则此处只要有一个拒绝，就结束
        if (var.endsWith("%")) {
            //任何一个拒绝就结束
            earlyTermination = Objects.equals("100%", var) && totalDenyUser > 0;
        } else {
            int needPassUser = Integer.parseInt(var);
            earlyTermination = totalPassUser > needPassUser;
        }
        if (earlyTermination) {
            //可以提前结束了
            parent.setVariable("nrOfInstances", totalUser);
            //剩余实例数量模拟仅差当前节点
            parent.setVariable("nrOfActiveInstances", 1);
            //完成实例数量模拟仅差当前节点
            parent.setVariable("nrOfCompletedInstances", totalUser - 1);
        }
        return earlyTermination;
    }

}
