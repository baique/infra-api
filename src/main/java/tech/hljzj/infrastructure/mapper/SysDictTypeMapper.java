
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysDictType;

/**
 * 字典类型 sys_dict_type
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
}