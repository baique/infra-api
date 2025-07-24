package tech.hljzj.infrastructure.vo.SysApp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.vo.SysApp.base.*;

import java.util.function.Consumer;

/**
 * 应用管理 sys_app
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppQueryVo extends SysAppQueryBaseVo<SysApp> {
    // 排序方式

    @Override
    public Consumer<LambdaQueryWrapper<? extends SysApp>> defaultSortBy() {
        return (q) -> {
            q.orderByAsc(SysApp::getSort);
            q.orderByDesc(SysApp::getCreateTime);
            q.orderByDesc(SysApp::getId);
        };
    }


    // 在这里可以扩展其他属性
}