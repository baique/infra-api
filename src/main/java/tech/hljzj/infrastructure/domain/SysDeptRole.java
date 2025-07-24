package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门关联角色 sys_dept_role_
 * DTO实体
 * 
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dept_role_")
public class SysDeptRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id_")
    private String id;
    /**
     * dept_id_
     */
    @TableField(value = "dept_id_")
    private String deptId;
    /**
     * role_id_
     */
    @TableField(value = "role_id_")
    private String roleId;
    /**
     * app_id_
     */
    @TableField(value = "app_id_")
    private String appId;
    /**
     * expired_time_
     */
    @TableField(value = "expired_time_")
    private Date expiredTime;
    /**
     * scope_
     */
    @TableField(value = "scope_")
    private Integer scope;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysDeptRole entity){
    }
}