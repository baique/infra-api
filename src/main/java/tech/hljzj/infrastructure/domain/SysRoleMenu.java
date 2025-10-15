package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色关联菜单 sys_role_menu_
 * DTO实体
 *
 * @author 
 */
@Getter
@Setter
@TableName(value = "sys_role_menu_")
public class SysRoleMenu implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 角色标识
     */
    @TableField(value = "role_id_")
    private String roleId;
    /**
     * 应用标识
     */
    @TableField(value = "app_id_")
    private String appId;
    /**
     * 菜单标识
     */
    @TableField(value = "menu_id_")
    private String menuId;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysRoleMenu entity){
    }
}