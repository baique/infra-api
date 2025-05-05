package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 功能扩展模型 sys_model_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_model_")
public class SysModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 绑定功能
     */
    @TableId(value = "module_")
    private String module;
    /**
     * 模型配置
     */
    @TableField(value = "editor_model_json_")
    private String editorModelJson;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysModel entity) {
        this.setModule(entity.getModule());
        this.setEditorModelJson(entity.getEditorModelJson());
    }
}