package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户管辖组织机构 sys_user_manager_dept_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_user_manager_dept_")
public class SysUserManagerDept implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 用户标识
     */
    @TableField(value = "user_id_")
    private String userId;
    /**
     * 部门标识
     */
    @TableField(value = "dept_id_")
    private String deptId;
    /**
     * 数据可见范围
     */
    @TableField(value = "data_access_scope_")
    private String dataAccessScope;
    /**
     * 是否包含子部门
     */
    @TableField(value = "contain_sub_")
    private String containSub;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysUserManagerDept entity) {
        this.setDataAccessScope(entity.getDataAccessScope());
        this.setContainSub(entity.getContainSub());
    }
}