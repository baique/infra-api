
package tech.hljzj.infrastructure.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysRole;

/**
 * 角色管理 sys_role
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysRoleMapper extends MPJBaseMapper<SysRole> {
}