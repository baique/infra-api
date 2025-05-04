package tech.hljzj.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.util.InjectUtil;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.VAR_PROCESS_MODEL_KEY;

public class TaskStartMonitor implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 获取流程执行上下文
        DelegateExecution execution = delegateTask.getExecution();
        String modelKey = execution.getVariable(VAR_PROCESS_MODEL_KEY, String.class);
        Registry registry = Context.getBean(Registry.class);
        SimpleProcessRegister processor = registry.get(modelKey);

        User processInitiatorUser = InjectUtil.getProcessInitiatorUser(execution.getVariables());

        processor.onStart(
            execution.getProcessInstanceBusinessKey(),
            delegateTask,
            processInitiatorUser
        );
    }
}
