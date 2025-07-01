
package tech.hljzj.infrastructure.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysUser;

/**
 * 用户管理 sys_user_
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysUserMapper extends MPJBaseMapper<SysUser> {
}