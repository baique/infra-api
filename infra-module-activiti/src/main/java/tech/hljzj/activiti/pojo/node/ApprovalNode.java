package tech.hljzj.activiti.pojo.node;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.*;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import tech.hljzj.activiti.listener.*;
import tech.hljzj.activiti.pojo.condition.Condition;
import tech.hljzj.activiti.pojo.enums.ApprovalMultiEnum;
import tech.hljzj.activiti.pojo.enums.AssigneeTypeEnum;

import java.util.*;

/**
 * @Title: ApprovalNode
 * @description：审批节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovalNode extends AssigneeNode {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();
    // 操作权限
    private Map<String, Boolean> operations = new LinkedHashMap<>();
    // 多人审批方式
    private ApprovalMultiEnum multi;
    // 多人会签通过率
    private String multiPercent;
    // 当通过时修改
    private List<Condition> onPassUpdate;
    // 当被拒绝时修改
    private List<Condition> onDenyUpdate;
    // 拒绝时链接到
    private String denyLink;
    // 通过时链接到
    private String passLink;
    // 流程通过时执行
    private List<String> onPassTrigger;
    // 流程拒绝时执行
    private List<String> onDenyTrigger;


    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 用户节点
        UserTask userTask = new UserTask();
        userTask.setId(this.getId());
        userTask.setName(this.getName());

        ArrayList<ActivitiListener> listeners = new ArrayList<>();

        // 任务被分配执行人时触发
        ActivitiListener startListener = new ActivitiListener();
        startListener.setEvent(TaskListener.EVENTNAME_ASSIGNMENT);
        startListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        startListener.setImplementation(TaskStartMonitor.class.getName());
        listeners.add(startListener);

        // 任务完成执行监听器
        ActivitiListener completeListener = new ActivitiListener();
        completeListener.setEvent(TaskListener.EVENTNAME_DELETE);
        completeListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        completeListener.setImplementation(TaskCompleteMonitor.class.getName());
        listeners.add(completeListener);

        userTask.setTaskListeners(listeners);

        // 任务清理
        ActivitiListener cleanListeners = new ActivitiListener();
        cleanListeners.setEvent(ExecutionListener.EVENTNAME_END);
        cleanListeners.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        cleanListeners.setImplementation(TaskCleanMonitor.class.getName());
        userTask.setExecutionListeners(Collections.singletonList(cleanListeners));

        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        if (this.getMulti() == ApprovalMultiEnum.SEQUENTIAL) {
            multiInstanceLoopCharacteristics.setSequential(true);
        } else if (this.getMulti() == ApprovalMultiEnum.JOINT) {
            multiInstanceLoopCharacteristics.setSequential(false);
            multiInstanceLoopCharacteristics.setCompletionCondition(("${nrOfCompletedInstances == nrOfInstances}"));
        }
        String variable = String.format("%sItem", this.getId());
        //这里的流程变量
        multiInstanceLoopCharacteristics.setElementVariable(variable);
        multiInstanceLoopCharacteristics.setInputDataItem(String.format("${%sCollection}", this.getId()));
        userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);
        // 这里根据类型来实现吧，如果是用户
        if (this.getAssigneeType().equals(AssigneeTypeEnum.USER)) {
            userTask.setAssignee(String.format("${%s}", variable));
        } else if (this.getAssigneeType().equals(AssigneeTypeEnum.ROLE)) {
            userTask.setCandidateGroups(Collections.singletonList(String.format("${%s}", variable)));
        }

        elements.add(userTask);

        // 下一个节点的连线
        Node child = this.getChild();

        String condition = "${_passCount >= 1}";
        String conditionElse = "${_passCount <= 0}";
        //获取一下条件
        if (this.getMulti() == ApprovalMultiEnum.JOINT) {
            String s = StrUtil.blankToDefault(this.getMultiPercent(), "100%");
            if (s.endsWith("%")) {
                condition = StrUtil.format("${(_passCount / nrOfInstances * 100) >= {}}", s.substring(0, s.length() - 1));
                conditionElse = StrUtil.format("${(_passCount / nrOfInstances * 100) < {}}", s.substring(0, s.length() - 1));
            } else {
                condition = StrUtil.format("${_passCount >= {}}", s.substring(0, s.length() - 1));
                conditionElse = StrUtil.format("${_passCount < {}}", s.substring(0, s.length() - 1));
            }
        }

        ActivitiListener passListener = new ActivitiListener();
        passListener.setEvent(ExecutionListener.EVENTNAME_END);
        passListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        passListener.setImplementation(TaskOnPassMonitor.class.getName());


        String passLink = this.getPassLink();
        SequenceFlow sequenceFlow;
        boolean linkToEnded = false;
        if (StrUtil.isBlank(passLink) || "continue".equals(passLink)) {
            linkToEnded = child instanceof EndNode;
            //继续向后流转
            sequenceFlow = this.buildSequence(child, passListener);
        } else {
            //前往指定位置，后续需要检查一下，如果这里指定的任务已经没了，就得提示一下发布失败
            sequenceFlow = this.buildSequence(passLink, passListener);
        }

        // 当前节点只有是通过状态，才会流转到目标节点
        sequenceFlow.setConditionExpression(condition);
        elements.add(sequenceFlow);

        // 反之，如果审批不通过
        ActivitiListener denyListener = new ActivitiListener();
        denyListener.setEvent(ExecutionListener.EVENTNAME_END);
        denyListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        denyListener.setImplementation(TaskOnDenyMonitor.class.getName());

        SequenceFlow onDenyToNext = this.buildSequence(
            StrUtil.blankToDefault(denyLink, "end"),
            denyListener
        );
        onDenyToNext.setId(onDenyToNext.getId() + "Deny");
        onDenyToNext.setConditionExpression(conditionElse);
        elements.add(onDenyToNext);

        // 下一个节点
        if (Objects.nonNull(child)) {
            child.setBranchId(this.getBranchId());
            List<FlowElement> flowElements = child.convert();
            elements.addAll(flowElements);
        }
        return elements;
    }

}
