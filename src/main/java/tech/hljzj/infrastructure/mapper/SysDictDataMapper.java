
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.hljzj.infrastructure.domain.SysDictData;

/**
 * 字典数据 sys_dict_data
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
}