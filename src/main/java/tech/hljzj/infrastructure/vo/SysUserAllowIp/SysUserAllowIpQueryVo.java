package tech.hljzj.infrastructure.vo.SysUserAllowIp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.infrastructure.vo.SysUserAllowIp.base.SysUserAllowIpQueryBaseVo;

import java.util.function.Consumer;

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserAllowIpQueryVo extends  SysUserAllowIpQueryBaseVo<SysUserAllowIp> {
    // 排序
    @Override
    public Consumer<LambdaQueryWrapper<? extends SysUserAllowIp>> defaultSortBy() {
        return super.defaultSortBy();
    }

  // 在这里可以扩展其他属性
}