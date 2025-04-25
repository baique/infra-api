package tech.hljzj.infrastructure.vo.SysRole;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tech.hljzj.infrastructure.vo.SysRole.base.*;

/**
 * 角色管理 sys_role 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
@Accessors(chain = true)
public class SysRoleQueryVo extends  SysRoleQueryBaseVo {
  // 在这里可以扩展其他属性
}