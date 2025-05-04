package tech.hljzj.activiti.pojo.node;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import tech.hljzj.activiti.listener.AssignMonitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Title: Node
 * @description：节点
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Node.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartNode.class, name = "start"),
        @JsonSubTypes.Type(value = ApprovalNode.class, name = "approval"),
        @JsonSubTypes.Type(value = ConditionNode.class, name = "condition"),
        @JsonSubTypes.Type(value = ExclusiveNode.class, name = "exclusive"),
        @JsonSubTypes.Type(value = EndNode.class, name = "end")
})
public abstract class Node implements Serializable {
    private static final long serialVersionUID = 132324315232123L;
    // 节点id
    private String id;
    // 父节点id
    private String pid;
    // 节点名称
    private String name;
    // 节点类型
    @JsonTypeId
    private String type;
    // 子节点
    private Node child;

    private String branchId;

    public abstract List<FlowElement> convert();

    public SequenceFlow buildSequence(Node next, ActivitiListener... listener) {
        String sourceRef;
        String targetRef;
        if (Objects.nonNull(next)) {
            sourceRef = next.getPid();
            targetRef = next.getId();
        } else if (StrUtil.isBlank(this.getBranchId())) {
            throw new RuntimeException(String.format("节点 %s 的下一个节点不能为空", this.id));
        } else {
            sourceRef = this.getId();
            targetRef = this.getBranchId();
        }
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(String.format("%s-%s", sourceRef, targetRef));
        sequenceFlow.setSourceRef(sourceRef);
        sequenceFlow.setTargetRef(targetRef);
        List<ActivitiListener> listeners = new ArrayList<>();
        listeners.add(AssignMonitor.defaultListener());
        listeners.addAll(Arrays.asList(listener));
        sequenceFlow.setExecutionListeners(listeners);
        return sequenceFlow;
    }

    public SequenceFlow buildSequence(String next, ActivitiListener... listener) {
        String sourceRef;
        String targetRef;
        if (Objects.nonNull(next)) {
            sourceRef = this.getId();
            targetRef = next;
        } else {
            sourceRef = this.getId();
            targetRef = "end";
        }
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(String.format("%s-%s", sourceRef, targetRef));
        sequenceFlow.setSourceRef(sourceRef);
        sequenceFlow.setTargetRef(targetRef);
        List<ActivitiListener> listeners = new ArrayList<>();
        listeners.add(AssignMonitor.defaultListener());
        listeners.addAll(Arrays.asList(listener));
        sequenceFlow.setExecutionListeners(listeners);
        return sequenceFlow;
    }

}
