package tech.hljzj.activiti.config;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.activiti.pojo.ActionTrigger;
import tech.hljzj.activiti.pojo.AssignExecutor;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.activiti.pojo.node.ProcessProp;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Loader {
    /**
     * 模型信息
     */
    private ProcessModel modelBase;
    /**
     * 流程集成的字段信息
     */
    private List<ProcessProp> fields = new ArrayList<>();
    /**
     * 授予执行的方法
     */
    private List<AssignExecutor> assignMethods = new ArrayList<>();
    /**
     * 执行触发器
     */
    private List<ActionTrigger> triggers = new ArrayList<>();

}
