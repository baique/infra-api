package tech.hljzj.infrastructure.vo.SysDictData;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.vo.SysDictData.base.SysDictDataQueryBaseVo;

import java.util.function.Consumer;

/**
 * 字典数据 sys_dict_data
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictDataQueryVo extends SysDictDataQueryBaseVo<SysDictData> {
    // 排序
    @Override
    public Consumer<LambdaQueryWrapper<? extends SysDictData>> defaultSortBy() {
        return (w) -> w.orderByAsc(SysDictData::getSort)
            .orderByDesc(SysDictData::getCreateTime)
            .orderByDesc(SysDictData::getId);
    }

    // 在这里可以扩展其他属性
}