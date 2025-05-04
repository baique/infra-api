package tech.hljzj.activiti.pojo.node;

import lombok.Data;

/**
 * @Title: FormProperty
 * @description：表单字段属性
 */
@Data
public class FormProperty {
    private String id;
    private String name;
    private Boolean readonly = false;
    private Boolean hidden;
    private Boolean required;
}
