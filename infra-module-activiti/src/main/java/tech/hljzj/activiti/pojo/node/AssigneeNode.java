package tech.hljzj.activiti.pojo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.FlowElement;
import tech.hljzj.activiti.pojo.enums.AssigneeTypeEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AssigneeNode extends Node {
    // 审批对象
    private AssigneeTypeEnum assigneeType;
    // 审批人
    private List<String> users;
    // 审批人角色
    private List<String> roles;
    /**
     * 自选
     */
    private String customFlag;

    public abstract List<FlowElement> convert();
}
