package tech.hljzj.activiti.listener;

import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.delegate.DelegateExecution;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.activiti.pojo.condition.Condition;
import tech.hljzj.activiti.pojo.node.ApprovalNode;
import tech.hljzj.activiti.pojo.node.Node;
import tech.hljzj.activiti.util.InjectUtil;

import java.util.List;
import java.util.Map;

/**
 * 当审批通过时更新
 */
public class TaskOnPassMonitor extends TaskStateMonitor {
    @Override
    protected void invoke(DelegateExecution execution, ApprovalNode completeNode, SimpleProcessRegister processor, ProcessModel processModel) {
        SequenceFlow currentFlowElement = (SequenceFlow) execution.getCurrentFlowElement();
        String targetRef = currentFlowElement.getTargetRef();

        Node nextDefine = processor.getTaskDefine(targetRef, processModel);
        ApprovalNode nextApproval = null;
        if (nextDefine != null) {
            if (nextDefine instanceof ApprovalNode) {
                nextApproval = (ApprovalNode) nextDefine;
            } else {
                //顺次向下找到下一个审批节点
                nextApproval = processor.getNextTaskDefine(nextDefine.getId(), processModel);
            }
        }
        Map<String, Object> variables = execution.getVariables();
        processor.onPass(
                execution.getProcessInstanceBusinessKey(),
                execution,
                completeNode,
                nextDefine,
                nextApproval,
                InjectUtil.getProcessInitiatorUser(variables)
        );
    }

    @Override
    protected List<String> getActions(ApprovalNode processNode) {
        return processNode.getOnPassTrigger();
    }

    @Override
    protected List<Condition> getFormProperties(ApprovalNode node) {
        return node.getOnPassUpdate();
    }
}
