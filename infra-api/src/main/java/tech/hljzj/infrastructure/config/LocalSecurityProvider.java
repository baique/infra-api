package tech.hljzj.infrastructure.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.AppObtainPassword;
import tech.hljzj.framework.security.SecurityProvider;
import tech.hljzj.framework.security.SystemUser;
import tech.hljzj.framework.security.bean.RoleInfo;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.app.AppScopeHolder;
import tech.hljzj.framework.util.password.SMUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocalSecurityProvider implements SecurityProvider {

    @Autowired
    private AppObtainPassword obtainPassword;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAppService sysAppService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserInfo login(Authentication authentication) throws Exception {
        String scopeAppId = StrUtil.blankToDefault(AppScopeHolder.getScopeAppId(), "0");
        SysApp app = sysAppService.getById(scopeAppId);
        if (app == null) {
            throw UserException.defaultError(MsgUtil.t("auth.illegalLoginRequests"));
        }
        String username = (String) authentication.getPrincipal();

        // 获取用户输入的明文密码
        String password = obtainPassword.obtainPassword((String) authentication.getCredentials());

        VSysUser principal = new VSysUser();
        principal.setUsername(username);
        principal = sysUserService.entityGet(principal, false);
        if (principal == null) {
            return null;
        }
        // 用户密码校验,这里如果当前密码为空，代表了这个迁移而来的数据，需要使用旧密码登录，旧密码需要携带加密方式和盐
        if (!validatePassword(password, principal)) {
            return null;
        }

        return buildLoginInfo(principal, app);
    }


    public UserInfo buildLoginInfo(VSysUser principal, SysApp scopeApp) {
        String scopeAppId = scopeApp.getId();

        SysDept deptInfo = sysDeptService.entityGet(principal.getDeptId());

        List<SysRole> sysRoles = sysUserService.listGrantRoleOfUser(principal.getId(), scopeAppId);
        Map<String, RoleInfo> roleMapping = sysRoles.stream()
                .map(f -> {
                    RoleInfo roleInfo = new RoleInfo();
                    roleInfo.setId(f.getId());
                    roleInfo.setKey(f.getKey());
                    roleInfo.setName(f.getName());
                    roleInfo.setMainHomePage(f.getMainPagePath());
                    roleInfo.setPriority(f.getPriority());
                    return roleInfo;
                }).collect(Collectors.toMap(RoleInfo::getKey, f -> f));
        //信息拼装
        UserInfo loginUser = new UserInfo();
        Set<RoleInfo> roleInfoList = loginUser.getRoleInfos();
        loginUser.getRole().addAll(roleMapping.keySet());
        roleInfoList.addAll(roleMapping.values());

        List<SysMenu> sysMenus;
        if (roleMapping.containsKey(SystemUser.GLOBAL_PERM)) {
            //这里检索全部
            SysMenuQueryVo q = new SysMenuQueryVo();
            q.setOwnerAppId(scopeAppId);
            sysMenus = sysMenuService.list(q);
        } else {
            sysMenus = sysRoleService.listGrantOfRoles(sysRoles.stream()
                    .map(SysRole::getId)
                    .collect(Collectors.toList()), scopeAppId, f -> f);
        }

        loginUser.setId(principal.getId())
                .setAppId(scopeAppId)
                .setAccount(principal.getUsername())
                .setName(principal.getRealname())
                .setPassword(principal.getPassword())
                .setIdCard(principal.getCardNo())
                .setPhone(principal.getPhone())
                .setEmail(principal.getEmail())
                .setOwnerDeptId(principal.getDeptId())
                .setOwnerDeptCode(principal.getDeptKey())
                .setOwnerDeptName(principal.getDeptName())
                .setUnit(principal.getWorkUnit())
                .setPost(principal.getWorkPos())
                .setRank(principal.getWorkRank())
                .setEnabled(Objects.equals(AppConst.YES, principal.getStatus()))
                .setLock(Objects.equals(AppConst.YES, principal.getAccountLock()))

                .setRawOwnerDept(deptInfo)
                .setRawUserInfo(principal)
        ;

        //设置用户主页路径
        loginUser.setMainHomePath(CollUtil.isEmpty(roleInfoList) ? scopeApp.getMainPagePath() : roleInfoList.stream().findFirst().map(RoleInfo::getMainHomePage).orElse(""));
        loginUser.setMainHomePath(StrUtil.blankToDefault(loginUser.getMainHomePath(), scopeApp.getMainPagePath()));

        loginUser.setPermission(sysMenus.stream()
                .map(SysMenu::getKey).collect(Collectors.toSet())
        );
        return loginUser;
    }

    public boolean validatePassword(String password, SysUser principal) {
        if (StrUtil.isNotBlank(principal.getPassword())) {
            // 本地的密码校验，本系统密码使用sm3
            return SMUtil.sm3(password).equals(principal.getPassword());
        }
        //此类可能是从其他系统迁移而来的数据
        if (Objects.isNull(principal.getPassword()) && StrUtil.isNotBlank(principal.getOldPassword())) {
            if (principal.getOldPassword().startsWith("tyrz:")) {
                String encPassword = DigestUtil.sha512Hex(password + "tyrz");
                return encPassword.equals(principal.getOldPassword().substring(5));
            }
            //...else if 其他的情况
        }
        return false;
    }

    @Override
    public boolean independentVerification() {
        return false;
    }
}

