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
 * sys_user_role_ sys_user_role_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_user_role_")
public class SysUserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 用户标识
     */
    @TableField(value = "user_id_")
    private String userId;
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
     * 角色过期时间
     */
    @TableField(value = "expired_time_")
    private Date expiredTime;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysUserRole entity) {
        this.setExpiredTime(entity.getExpiredTime());
    }
}