package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.config.mybatis.JsonTypeHandler;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户扩展信息 sys_user_ext_attr
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_user_ext_attr_")
public class SysUserExtAttr implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 用户标识，与用户一对一
     */
    @TableId(value = "id_")
    private String id;
    /**
     * 扩展属性
     */
    @TableField(value = "attribution_", typeHandler = JsonTypeHandler.class)
    private Map<String, Object> attribution;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysUserExtAttr entity) {
        this.setId(entity.getId());
        this.setAttribution(entity.getAttribution());
    }
}