package tech.hljzj.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.SimpleProcessRegister;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.VAR_PROCESS_MODEL_KEY;

/**
 * 当流程结束时，what are you want to do
 */
public class ProcessCompleteMonitor extends TaskCompleteMonitor implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
        String variable = execution.getVariable(VAR_PROCESS_MODEL_KEY, String.class);
        SimpleProcessRegister manager = Context.getBean(Registry.class).get(variable);
        manager.onProcessEnd(processInstanceBusinessKey);
    }
}
