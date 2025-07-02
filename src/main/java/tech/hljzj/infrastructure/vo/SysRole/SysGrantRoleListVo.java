package tech.hljzj.infrastructure.vo.SysRole;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 角色管理 sys_role 
 * 交互实体 用于返回已被授予的角色信息
 *
 * @author wa
 */
@Getter
@Setter
public class SysGrantRoleListVo extends SysRoleListVo {
    // 在这里可以扩展其他属性
    private Date expiredTime;
    private int scope;
}