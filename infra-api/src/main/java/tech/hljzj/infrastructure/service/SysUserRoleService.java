package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysUserRole;

public interface SysUserRoleService extends IService<SysUserRole> {
    boolean exists(Wrapper<SysUserRole> eq);
}
