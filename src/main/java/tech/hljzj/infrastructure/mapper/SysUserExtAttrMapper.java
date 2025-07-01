
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysUserExtAttr;

/**
 * 用户扩展信息 sys_user_ext_attr
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysUserExtAttrMapper extends BaseMapper<SysUserExtAttr> {
}