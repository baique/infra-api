package tech.hljzj.activiti.pojo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.*;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.listener.ProcessStartMonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Title: StartNode
 * @description：启动节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StartNode extends Node {
    // 表单属性
    private List<FormProperty> formProperties = new ArrayList<>();

    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 开始节点
        StartEvent startEvent = new StartEvent();
        startEvent.setId(this.getId());
        startEvent.setName(this.getName());


        ActivitiListener event = new ActivitiListener();
        event.setEvent(ExecutionListener.EVENTNAME_START);
        event.setImplementation(ProcessStartMonitor.class.getName());
        event.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        startEvent.setExecutionListeners(Collections.singletonList(
                event
        ));

        elements.add(startEvent);
        // 下一个节点的连线
        Node child = this.getChild();
        SequenceFlow sequenceFlow = this.buildSequence(child);
        elements.add(sequenceFlow);
        // 下一个节点
        if (Objects.nonNull(child)) {
            List<FlowElement> flowElements = child.convert();
            elements.addAll(flowElements);
        }
        return elements;
    }
}
