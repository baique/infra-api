
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysDeptExternalUser;

/**
 * 外部编入人员 sys_dept_external_user_
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysDeptExternalUserMapper extends BaseMapper<SysDeptExternalUser> {
}