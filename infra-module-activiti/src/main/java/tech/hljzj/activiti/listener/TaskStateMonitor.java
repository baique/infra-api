package tech.hljzj.activiti.listener;

import cn.hutool.core.util.StrUtil;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.ActException;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.condition.Condition;
import tech.hljzj.activiti.pojo.node.ApprovalNode;
import tech.hljzj.activiti.util.InjectUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.*;

/**
 * 当审批通过时更新
 */
public abstract class TaskStateMonitor implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        String currentModeKey = delegateExecution.getVariable(VAR_PROCESS_MODEL_KEY, String.class);
        SimpleProcessRegister processor = Context.getBean(Registry.class).get(currentModeKey);
        String deployId = delegateExecution.getVariable(VAR_DEPLOY_ID, String.class);
        ProcessModel processModel;
        try {
            processModel = processor.getModel(deployId);
        } catch (IOException e) {
            throw new ActException("模型读取失败!", e);
        }
        ApprovalNode completeNode = getProcessNode(delegateExecution, processor, processModel);

        invoke(delegateExecution, completeNode, processor, processModel);

        Map<String, Object> variables = delegateExecution.getVariables();
        User processInitiatorUser = InjectUtil.getProcessInitiatorUser(variables);

        //审批完成后更新表单属性状态
        updateFormProperties(processInitiatorUser, delegateExecution, getFormProperties(completeNode), processor);

        //审批完成后触发动作
        triggerAction(processInitiatorUser, delegateExecution, getActions(completeNode), processor);

        //删除掉以下变量
        delegateExecution.removeTransientVariable(VAR_PASS_COUNT);
        delegateExecution.removeTransientVariable(VAR_DENY_COUNT);
    }

    /**
     * 执行一些内容
     *
     * @param delegateExecution 执行器
     * @param completeNode      完成的节点
     * @param processor    流程管理
     * @param processModel      当前流程模型
     */
    protected abstract void invoke(DelegateExecution delegateExecution, ApprovalNode completeNode, SimpleProcessRegister processor, ProcessModel processModel);

    protected abstract List<String> getActions(ApprovalNode processNode);

    protected abstract List<Condition> getFormProperties(ApprovalNode node);

    private ApprovalNode getProcessNode(DelegateExecution delegateExecution, SimpleProcessRegister processor, ProcessModel processModel) {
        SequenceFlow currentFlowElement = (SequenceFlow) delegateExecution.getCurrentFlowElement();
        String taskDefineId = currentFlowElement.getSourceRef();
        return (ApprovalNode) processor.getTaskDefine(taskDefineId, processModel);
    }

    private void triggerAction(User processInitiatorUser, DelegateExecution delegateExecution, List<String> actions, SimpleProcessRegister processor) {
        if (actions == null) {
            return;
        }
        processor.triggerMethod(delegateExecution.getProcessInstanceBusinessKey(),
                actions, processInitiatorUser);
    }

    private void updateFormProperties(User processInitiatorUser, DelegateExecution delegateExecution, List<Condition> conditionList, SimpleProcessRegister processor) {
        if (conditionList == null) {
            return;
        }
        String processInstanceId = delegateExecution.getProcessInstanceId();
        for (Condition condition : conditionList) {
            if (StrUtil.isBlank(condition.getField()) || StrUtil.isBlankIfStr(condition.getValue())) {
                continue;
            }
            //调用更新属性
            processor.updateProp(
                    processInstanceId,
                    condition.getField(),
                    condition.getValue().toString(),
                    processInitiatorUser
            );
        }
    }
}
