
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysConfig;

/**
 * 系统配置 sys_config
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}