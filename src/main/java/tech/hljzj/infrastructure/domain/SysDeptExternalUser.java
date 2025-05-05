package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 外部编入人员 sys_dept_external_user_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dept_external_user_")
public class SysDeptExternalUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 部门标识
     */
    @TableField(value = "dept_id_")
    private String deptId;
    /**
     * 用户标识
     */
    @TableField(value = "user_id_")
    private String userId;
    /**
     * 用户身份
     */
    @TableField(value = "identity_")
    private String identity;
    /**
     * 备注信息
     */
    @TableField(value = "remarks_")
    private String remarks;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysDeptExternalUser entity) {
        this.setDeptId(entity.getDeptId());
        this.setUserId(entity.getUserId());
        this.setIdentity(entity.getIdentity());
        this.setRemarks(entity.getRemarks());
    }
}