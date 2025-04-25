package tech.hljzj.infrastructure.vo.SysUser;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.vo.SysUser.base.SysUserNewBaseVo;

import java.util.Map;

/**
 * 用户管理 sys_user_
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserNewVo extends SysUserNewBaseVo {
    // 在这里可以扩展其他属性
    /**
     * 用户的扩展属性
     */
    private Map<String, Object> attribution;
}