package tech.hljzj.infrastructure.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.security.SystemUser;
import tech.hljzj.framework.security.bean.RoleInfo;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.service.SysMenuService;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserLoginService;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppLoginUserInfo extends UserInfo {
    private SysUserLoginService sysUserLoginService;
    private SysRoleService sysRoleService;
    private SysMenuService sysMenuService;

    public AppLoginUserInfo(SysUserLoginService sysUserLoginService, SysRoleService sysRoleService, SysMenuService sysMenuService) {
        this.sysUserLoginService = sysUserLoginService;
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
    }

    public AppLoginUserInfo() {
        this.roleLoaded = true;
        this.menuLoaded = true;
    }

    @Getter
    @Setter
    private String loginAppId;
    private boolean roleLoaded;

    private synchronized void loadRoleInfo() {
        if (roleLoaded) {
            return;
        }
        roleLoaded = true;
        List<SysLoginBindRole> sysRoles = sysUserLoginService.listGrantRoleOfUserAndDept(this.getId(), this.getLoginAppId(), this.getOwnerDeptId());
        Map<String, RoleInfo> roleMapping = sysRoles.stream()
            .map(f -> {
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setId(f.getId());
                roleInfo.setKey(f.getKey());
                roleInfo.setName(f.getName());
                roleInfo.setMainHomePage(f.getMainPagePath());
                roleInfo.setPriority(f.getPriority());
                roleInfo.setSource(f.getSource());
                return roleInfo;
            }).collect(Collectors.toMap(RoleInfo::getKey, f -> f));
        this.getRole().addAll(roleMapping.keySet());
        this.getRoleInfos().addAll(roleMapping.values());
    }

    private boolean menuLoaded;

    private synchronized void loadMenuInfo() {
        loadRoleInfo();
        if (menuLoaded) {
            return;
        }
        menuLoaded = true;
        List<SysMenu> sysMenus;
        if (this.getRole().contains(SystemUser.GLOBAL_PERM)) {
            //这里检索全部
            SysMenuQueryVo q = new SysMenuQueryVo();
            q.setOwnerAppId(this.getLoginAppId());
            sysMenus = sysMenuService.list(q);
        } else {
            sysMenus = sysRoleService.listGrantOfRoles(this.getRoleInfos().stream()
                .map(RoleInfo::getId)
                .collect(Collectors.toList()), this.getLoginAppId(), f -> f);
        }

        if (CollUtil.isNotEmpty(this.getRole())) {
            //设置用户主页路径
            this.setMainHomePath(this.getRoleInfos().stream().sorted().findFirst().map(RoleInfo::getMainHomePage).orElse(
                this.getMainHomePath()
            ));
        }
        this.setPermission(sysMenus.stream().map(SysMenu::getKey).collect(Collectors.toSet()));
    }

    @Override
    public String getMainHomePath() {
        loadRoleInfo();
        return super.getMainHomePath();
    }

    @Override
    public UserInfo addRole(String role) {
        loadRoleInfo();
        return super.addRole(role);
    }

    @Override
    public UserInfo addPermission(String permission) {
        loadMenuInfo();
        return super.addPermission(permission);
    }

    @Override
    public Set<String> getRole() {
        loadRoleInfo();
        return super.getRole();
    }

    @Override
    public Set<RoleInfo> getRoleInfos() {
        loadRoleInfo();
        return super.getRoleInfos();
    }

    @Override
    public Set<String> getPermission() {
        loadMenuInfo();
        return super.getPermission();
    }
}
