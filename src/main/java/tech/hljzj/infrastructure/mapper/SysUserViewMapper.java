package tech.hljzj.infrastructure.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.VSysUser;

@Mapper
public interface SysUserViewMapper extends MPJBaseMapper<VSysUser> {
}
