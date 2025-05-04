package tech.hljzj.activiti.listener;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.ActException;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.node.Node;
import tech.hljzj.activiti.util.InjectUtil;

import java.io.IOException;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.VAR_DEPLOY_ID;
import static tech.hljzj.activiti.manager.SimpleProcessRegister.VAR_PROCESS_MODEL_KEY;

public class AssignMonitor implements ExecutionListener {
    public static ActivitiListener defaultListener() {
        ActivitiListener event = new ActivitiListener();
        event.setEvent(ExecutionListener.EVENTNAME_END);
        event.setImplementation(AssignMonitor.class.getName());
        event.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        return event;
    }

    @Override
    public void notify(DelegateExecution execution) {
        if (!execution.isEnded()) {
            SequenceFlow currentFlowElement = (SequenceFlow) execution.getCurrentFlowElement();

            //此处将会获取下一个任务，为下一个任务设置执行人信息
            String modelKey = execution.getVariable(VAR_PROCESS_MODEL_KEY, String.class);
            String deployId = execution.getVariable(VAR_DEPLOY_ID, String.class);
            SimpleProcessRegister manager = Context.getBean(Registry.class).get(modelKey);
            Node nextTaskDefine;
            try {
                nextTaskDefine = manager.getTaskDefine(currentFlowElement.getTargetRef(), deployId);
            } catch (IOException e) {
                throw new ActException("模型读取失败!", e);
            }
            InjectUtil.injectDynamicExecutor(
                    execution.getProcessInstanceBusinessKey(),
                    nextTaskDefine,
                    execution.getParent(),
                    manager
            );
        }
    }
}
