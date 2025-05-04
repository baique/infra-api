package tech.hljzj.activiti.pojo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;

import java.util.*;

/**
 * @Title: CcNode
 * @description：抄送节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CcNode extends AssigneeNode {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();
    // 操作权限
    private Map<String, Boolean> operations = new LinkedHashMap<>();

    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 服务节点
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setId(this.getId());
        serviceTask.setName(this.getName());
        // serviceTask.setAsynchronous(true);
        serviceTask.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        serviceTask.setImplementation("${ccDelegate}");
        elements.add(serviceTask);
        // 下一个节点的连线
        Node child = this.getChild();
        SequenceFlow sequenceFlow = this.buildSequence(child);
        elements.add(sequenceFlow);
        // 下一个节点
        if (Objects.nonNull(child)) {
            child.setBranchId(this.getBranchId());
            List<FlowElement> flowElements = child.convert();
            elements.addAll(flowElements);
        }
        return elements;
    }

}
