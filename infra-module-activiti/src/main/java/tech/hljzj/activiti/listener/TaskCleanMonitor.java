package tech.hljzj.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.manager.SimpleProcessRegister;

import java.util.Objects;

public class TaskCleanMonitor implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        Boolean isRollback = execution.getVariable(SimpleProcessRegister.VAR_IS_ROLLBACK, Boolean.class);
        if (Objects.equals(true, isRollback)) {
            execution.removeVariable(SimpleProcessRegister.VAR_PREV_TASK);//如果撤回，就要删除任务标识
        }
        execution.removeVariable(execution.getCurrentActivityId() + "Collection");
        execution.removeVariable(execution.getCurrentActivityId() + "Item");
        execution.removeVariableLocal("nrOfInstances");
        execution.removeVariableLocal("nrOfActiveInstances");
        execution.removeVariableLocal("nrOfCompletedInstances");
        execution.removeVariableLocal("loopCounter");
        execution.removeVariable(SimpleProcessRegister.VAR_IS_ROLLBACK);
    }
}
