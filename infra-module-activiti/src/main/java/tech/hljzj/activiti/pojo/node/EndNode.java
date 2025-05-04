package tech.hljzj.activiti.pojo.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.engine.delegate.ExecutionListener;
import tech.hljzj.activiti.listener.ProcessCompleteMonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Title: EndNode
 * @description：结束节点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EndNode extends Node {

    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 结束节点
        EndEvent endEvent = new EndEvent();
        endEvent.setId(this.getId());
        endEvent.setName(this.getName());

        ActivitiListener event = new ActivitiListener();
        event.setEvent(ExecutionListener.EVENTNAME_START);
        event.setImplementation(ProcessCompleteMonitor.class.getName());
        event.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        endEvent.setExecutionListeners(Collections.singletonList(
                event
        ));

        elements.add(endEvent);
        return elements;
    }
}
