package tech.hljzj.infrastructure.vo.SysActiviti;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysActiviti;
import tech.hljzj.infrastructure.vo.SysActiviti.base.*;
import java.util.function.Consumer;

/**
 * 流程管理 sys_activiti_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysActivitiQueryVo extends  SysActivitiQueryBaseVo<SysActiviti> {
    // 排序
    @Override
    public Consumer<LambdaQueryWrapper<? extends SysActiviti>> defaultSortBy() {
        return super.defaultSortBy();
    }

  // 在这里可以扩展其他属性
}