package tech.hljzj.infrastructure.vo.SysDeptIdentity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDeptIdentity;
import tech.hljzj.infrastructure.vo.SysDeptIdentity.base.SysDeptIdentityQueryBaseVo;

import java.util.function.Consumer;

/**
 * 岗位管理 sys_dept_identity_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptIdentityQueryVo extends SysDeptIdentityQueryBaseVo<SysDeptIdentity> {
    // 排序
    @Override
    public Consumer<LambdaQueryWrapper<? extends SysDeptIdentity>> defaultSortBy() {
        return (builder) -> {
            builder.orderByAsc(SysDeptIdentity::getSort);
            builder.orderByDesc(SysDeptIdentity::getId);
        };
    }

    // 在这里可以扩展其他属性
}