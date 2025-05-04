package tech.hljzj.activiti.pojo;

import lombok.Data;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import tech.hljzj.activiti.pojo.node.Node;

import java.util.List;

/**
 * @Title: ProcessModel
 * @description：流程模型
 */
@Data
public class ProcessModel {
    /**
     * 流程code
     */
    private String code;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程本体
     */
    private Node process;

    public BpmnModel toBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();
        // 命名空间
        bpmnModel.setTargetNamespace("http://b3mn.org/stencilset/bpmn2.0");
        // 创建一个流程
        Process process = new Process();
        // 设置流程的id
        process.setId(this.getCode());
        // 设置流程的name
        process.setName(this.getName());
        // 递归构建所有节点
        Node node = this.getProcess();
        List<FlowElement> flowElementList = node.convert();
        for (FlowElement flowElement : flowElementList) {
            process.addFlowElement(flowElement);
        }
        // 设置流程
        bpmnModel.addProcess(process);
        return bpmnModel;
    }
}
