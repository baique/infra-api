package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.domain.SysUserRole;
import tech.hljzj.infrastructure.mapper.SysUserRoleMapper;
import tech.hljzj.infrastructure.service.SysUserRoleService;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    public boolean exists(Wrapper<SysUserRole> eq) {
        return baseMapper.exists(eq);
    }
}
