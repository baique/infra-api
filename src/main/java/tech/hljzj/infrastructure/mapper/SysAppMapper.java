
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysApp;

/**
 * 应用管理 sys_app
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysAppMapper extends BaseMapper<SysApp> {
}