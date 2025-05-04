package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.activiti.pojo.node.ProcessProp;

import java.util.List;
import java.util.Map;

/**
 * 表单信息
 */
@Getter
@Setter
public class FormInfo {
    /**
     * 属性列表
     */
    private List<ProcessProp> props;
    /**
     * 允许的操作
     */
    private Map<String, Boolean> operations;
}
