package tech.hljzj.infrastructure.vo.SysDeptRole;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDeptRole;
import tech.hljzj.infrastructure.vo.SysDeptRole.base.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.function.Consumer;

/**
 * 部门关联角色 sys_dept_role_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptRoleQueryVo extends  SysDeptRoleQueryBaseVo<SysDeptRole> {
    // 排序
    @Override
    public Consumer<LambdaQueryWrapper<? extends SysDeptRole>> defaultSortBy() {
        return super.defaultSortBy();
    }

  // 在这里可以扩展其他属性
}