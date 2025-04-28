package tech.hljzj.infrastructure.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.cache.CommonCache;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.AppObtainPassword;
import tech.hljzj.framework.security.SecurityProvider;
import tech.hljzj.framework.security.SystemUser;
import tech.hljzj.framework.security.bean.RoleInfo;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.password.SMUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
@Primary
public class LocalSecurityProvider implements SecurityProvider {

    @Autowired
    private AppObtainPassword obtainPassword;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAppService sysAppService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private CommonCache commonCache;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysUserLockService sysUserLockService;

    @Override
    public Map<String, String> withTokenAttr() {
        Map<String, String> tokenAttr = new LinkedHashMap<>();
        tokenAttr.put("appId", AppScopeHolder.requiredScopeAppId());
        return tokenAttr;
    }

    @Override
    public UserInfo login(Authentication authentication) throws Exception {
        String scopeAppId = AppScopeHolder.requiredScopeAppId();
        SysApp app = sysAppService.getOne(Wrappers.<SysApp>lambdaQuery()
                        .eq(SysApp::getId, scopeAppId)
                        .or()
                        .eq(SysApp::getKey, scopeAppId)
                , false);
        if (app == null) {
            throw UserException.defaultError(MsgUtil.t("auth.illegalLoginRequests"));
        }
        String username = (String) authentication.getPrincipal();
        if (sysUserLockService.isLocked(username)) {
            // 这里不考虑跨年情况，以展示为主
            throw UserException.defaultError("由于您多次登录失败，当前账号已被锁定");
        }

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
            int mxLockCount = Integer.parseInt(sysConfigService.getValueByKey(AppConst.CONFIG_MAX_TRY_LOGIN_COUNT));
            if (mxLockCount > 0) {
                String key = "login:fail:" + username;
                Object v = commonCache.get(key);
                int prevFailCount = 0;
                if (!StrUtil.isBlankIfStr(v)) {
                    prevFailCount = Integer.parseInt(v.toString());
                }
                prevFailCount += 1;
                // 如果用户持续失败
                commonCache.put(key, prevFailCount, 1L, TimeUnit.HOURS);
                if (prevFailCount > mxLockCount) {
                    sysUserLockService.lock(username);
                    throw UserException.defaultError("由于您多次登录失败，当前账号已被锁定");
                }
            }
            return null;
        }

        return buildLoginInfo(principal, app);
    }

    public UserInfo buildLoginInfo(VSysUser principal, SysApp scopeApp) {
        String scopeAppId = scopeApp.getId();

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
        AppLoginUserInfo loginUser = new AppLoginUserInfo();
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

        loginUser.setLoginAppId(scopeAppId);
        loginUser.setId(principal.getId())
                .setAccount(principal.getUsername())
                .setName(principal.getRealname())
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

