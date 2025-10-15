package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户IP绑定信息 sys_user_allow_ip_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_user_allow_ip_")
public class SysUserAllowIp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id_")
    private String id;
    /**
     * 用户标识
     */
    @TableField(value = "user_id_")
    private String userId;
    /**
     * 绑定IP
     */
    @TableField(value = "allow_ip_")
    private String allowIp;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysUserAllowIp entity) {
        this.setUserId(entity.getUserId());
        this.setAllowIp(entity.getAllowIp());
    }
}