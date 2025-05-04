package tech.hljzj.activiti.pojo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.FlowElement;

import java.util.List;

/**
 * @Title: BranchNode
 * @description：分支节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BranchNode extends Node {
    private List<ConditionNode> children;

    public abstract List<FlowElement> convert();

}
