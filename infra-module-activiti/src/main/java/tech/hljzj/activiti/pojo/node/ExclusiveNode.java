package tech.hljzj.activiti.pojo.node;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Title: ExclusiveNode
 * @description：独占分支
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExclusiveNode extends BranchNode {


    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 独占分支
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(this.getId());
        exclusiveGateway.setName(this.getName());
        elements.add(exclusiveGateway);
        List<ConditionNode> children = this.getChildren();
        children.stream()
                .filter(ConditionNode::getDef)
                .findFirst()
                .ifPresent(conditionNode -> {
                    exclusiveGateway.setDefaultFlow(conditionNode.getId());
                });
        // 子节点
        if (!CollectionUtils.isEmpty(children)) {
            for (ConditionNode next : children) {
                if (next.getDef()) {
                    //默认条件是结束流程
                    next.setBranchId(StrUtil.blankToDefault(next.getBranchId(), "end"));
                } else {
                    //其子集的next指向全部改为与当前相同
                    String branchId = Optional.ofNullable(this.getChild()).map(Node::getId).orElse(this.getBranchId());
                    next.setBranchId(branchId);
                }
                elements.addAll(next.convert());
            }
        }
        // 下一个节点
        Node child = this.getChild();
        if (Objects.nonNull(child)) {
            child.setBranchId(this.getBranchId());
            List<FlowElement> flowElements = child.convert();
            elements.addAll(flowElements);
        }
        return elements;
    }
}
