package tech.hljzj.activiti.register;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import tech.hljzj.activiti.pojo.ActionTrigger;
import tech.hljzj.activiti.pojo.AssignExecutor;
import tech.hljzj.activiti.pojo.TaskProgress;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.node.ApprovalNode;
import tech.hljzj.activiti.pojo.node.Node;
import tech.hljzj.activiti.pojo.node.ProcessProp;

import java.util.List;
import java.util.Map;

/**
 * 流程注册
 *
 * @author wa
 */
public interface IProcess {

    /**
     * 流程标识
     *
     * @return 流程标识
     */
    String processCode();

    /**
     * 流程名称
     *
     * @return 流程名称
     */
    String processName();

    /**
     * 流程过程中需要用到的变量列表
     *
     * @return 流程变量列表
     */
    List<ProcessProp> props();


    /**
     * 返回支持的审批人注入方式
     *
     * @return 审批人注入方式
     */
    List<AssignExecutor> assignMethod();

    /**
     * 自定义动作列表
     *
     * @return 动作
     */
    List<ActionTrigger> actionTriggerMethod();

    /**
     * 分配执行人
     *
     * @param processId        流程ID
     * @param assignTag        动态分配方式
     * @param processInitiator 流程发起人
     * @param variables        流程变量
     * @return 审批人列表
     */
    List<User> assignExecutor(String processId, String assignTag, User processInitiator, List<String> roles, Map<String, Object> variables);

    /**
     * 修改属性
     *
     * @param processId        流程ID
     * @param propKey          属性标识
     * @param newValue         属性值
     * @param processInitiator 流程创建人信息
     */
    void updateProp(String processId, String propKey, String newValue, User processInitiator);

    /**
     * 执行动作
     *
     * @param processId        流程ID
     * @param actions          动作列表（可能为空！！）
     * @param processInitiator 流程创建人信息
     */
    void triggerMethod(String processId, List<String> actions, User processInitiator);


    /**
     * 当审批任务开始时
     *
     * @param processId        流程id
     * @param task             任务信息
     * @param processInitiator 流程创建任务信息
     */
    default void onStart(
        String processId,
        DelegateTask task,
        User processInitiator
    ) {

    }

    /**
     * 任务完成（此处的完成并不感知审批通过还是拒绝）
     * <p>
     * 多人会签或签时此方法会被多次调用，可以通过当前环节状态中的完成状态判定是否最后一次调用
     *
     * @param processId        流程ID
     * @param task             任务信息
     * @param processInitiator 流程创建人
     * @param assign           当前处理人
     * @param pass             当前处理人是否选择了通过
     * @param progress         当前环节状态
     */
    default void onComplete(
        String processId,
        DelegateTask task,
        User processInitiator,
        String assign,
        boolean pass,
        TaskProgress progress
    ) {

    }

    /**
     * 当某个任务审批通过时调用
     *
     * @param processId        流程id
     * @param execution        上下文
     * @param completeNode     当前任务节点信息
     * @param nextNode         下一任务节点信息（这里不一定是任务，也可能是判断分支条件等等……）
     * @param nextApproval     下一审批节点的信息（这里可能为空，也可能出现回环，如审批不通过或通过时直接跳转到某位置）
     * @param processInitiator 流程创建人的信息
     */
    default void onPass(String processId,
                        DelegateExecution execution,
                        ApprovalNode completeNode,
                        Node nextNode,
                        ApprovalNode nextApproval,
                        User processInitiator) {

    }

    /**
     * 当某个任务审批拒绝时调用
     *
     * @param processId        流程id
     * @param execution        上下文
     * @param completeNode     当前任务节点信息
     * @param nextNode         下一任务节点信息（这里不一定是任务，也可能是判断分支条件等等……）
     * @param nextApproval     下一审批节点的信息（这里可能为空，也可能出现回环，如审批不通过或通过时直接跳转到某位置）
     * @param processInitiator 流程创建人的信息
     */
    default void onDeny(String processId,
                        DelegateExecution execution,
                        ApprovalNode completeNode,
                        Node nextNode,
                        ApprovalNode nextApproval,
                        User processInitiator) {

    }


    /**
     * 流程开始
     *
     * @param processId 流程ID
     */
    default void onProcessStart(String processId) {

    }

    /**
     * 流程结束
     *
     * @param processId 流程id
     */
    default void onProcessEnd(String processId) {

    }
}
