
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysLog;

/**
 * 操作日志 sys_log_
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}