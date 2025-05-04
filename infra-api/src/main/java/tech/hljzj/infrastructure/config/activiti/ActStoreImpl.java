package tech.hljzj.infrastructure.config.activiti;

import cn.hutool.core.collection.CollUtil;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.activiti.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tech.hljzj.activiti.pojo.Role;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.register.ActStoreLoader;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActStoreImpl implements ActStoreLoader {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    public ActStoreImpl(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }


    @Override
    public List<User> users(List<Serializable> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            VSysUserQueryVo q = new VSysUserQueryVo();
            q.setIdIn(ids.stream().map(Object::toString).collect(Collectors.toList()));
            return sysUserService.list(q).stream().map(f -> {
                User user = new User();
                user.setId(f.getId());
                user.setName(f.getRealname() + "(" + f.getUsername() + ")");
                return user;
            }).collect(Collectors.toList());
        }
        return sysUserService.list(new VSysUserQueryVo()).stream().map(f -> {
            User user = new User();
            user.setId(f.getId());
            user.setName(f.getRealname() + "(" + f.getUsername() + ")");
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Role> roles(List<Serializable> ids) {
        SysRoleQueryVo q = new SysRoleQueryVo();
        q.setOwnerAppId(AppConst.ID);
        if (CollUtil.isNotEmpty(ids)) {
            q.setKeyIn(ids.stream().map(Object::toString).collect(Collectors.toList()));

            return sysRoleService.list(q).stream().map(f -> {
                Role role = new Role();
                role.setId(f.getKey());
                role.setName(f.getName());
                return role;
            }).collect(Collectors.toList());
        }

        return sysRoleService.list(q).stream().map(f -> {
            Role role = new Role();
            role.setId(f.getKey());
            role.setName(f.getName());
            return role;
        }).collect(Collectors.toList());
    }
}
