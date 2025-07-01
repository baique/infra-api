
package tech.hljzj.infrastructure.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysRoleMenu;

/**
 * 菜单管理 sys_role_menu
 * DTO操作层
 *
 * @author wa
 */
@Mapper
public interface SysRoleMenuMapper extends MPJBaseMapper<SysRoleMenu> {
}