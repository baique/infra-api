package tech.hljzj.infrastructure.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.AppObtainPassword;
import tech.hljzj.framework.security.SecurityProvider;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.password.SMUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.framework.util.web.ReqUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.protect.password.PasswordNotSafeException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@Primary
public class LocalSecurityProvider implements SecurityProvider, InitializingBean {
    public static final String PASSWORD_EXPIRED = "密码已过期，请修改后重新登录";

    @Value("${security.password.super:}")
    private String superPassword;
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
    private SysConfigService sysConfigService;
    @Autowired
    private SysUserLockService sysUserLockService;
    @Autowired
    private SysUserLoginService sysUserLoginService;
    @Autowired
    @Lazy
    private TokenAuthenticateService tokenAuthenticateService;
    @Autowired
    private SysUserAllowIpService sysUserAllowIpService;


    @Override
    public Map<String, String> withTokenAttr(UserInfo userInfo) {
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
        if (StrUtil.isBlank(username)) {
            throw UserException.defaultError("登录用户不允许为空");
        }
        String encPassword = (String) authentication.getCredentials();
        if (StrUtil.isBlank(encPassword)) {
            throw UserException.defaultError("登录密码不允许为空");
        }
        if (sysUserLockService.isLocked(username)) {
            // 这里不考虑跨年情况，以展示为主
            throw UserException.defaultError("由于您多次登录失败，当前账号已被锁定");
        }
        String password;
        try {
            // 获取用户输入的明文密码
            password = obtainPassword.obtainPassword(encPassword);
        } catch (Exception e) {
            throw UserException.defaultError("用户密码无法正确解析，可能是其不符合安全要求", e);
        }

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
                int prevFailCount = sysUserLockService.incrLoginFail(username);
                if (prevFailCount > mxLockCount) {
                    sysUserLockService.lock(username);
                    throw UserException.defaultError("由于您多次登录失败，当前账号已被锁定");
                }
            }
            return null;
        }

        // 检查登录IP
        List<String> allowIpList = sysUserAllowIpService.list(Wrappers.lambdaQuery(SysUserAllowIp.class).eq(SysUserAllowIp::getUserId, principal.getId())).stream().map(SysUserAllowIp::getAllowIp).collect(Collectors.toList());

        validateBindIp(username, allowIpList);

        validatePasswordStorage(password, principal);

        if (AppConst.PASSWORD_POLICY.EXPIRED.equals(principal.getPasswordPolicy())) {
            throw new PasswordNotSafeException("密码已过期，请修改后重新登录；或联系管理员进行解锁");
        } else if (AppConst.PASSWORD_POLICY.EXPIRED_AT.equals(principal.getPasswordPolicy())) {
            if (principal.getPasswordExpired() == null || principal.getPasswordExpired().before(new Date())) {
                throw new PasswordNotSafeException("密码已过期，请修改后重新登录；或联系管理员进行解锁");
            }
        } else if (AppConst.PASSWORD_POLICY.NEXT_LOGIN_TIME_MUST_UPDATE.equals(principal.getPasswordPolicy())) {
            throw new PasswordNotSafeException("密码已过期，请修改后重新登录；或联系管理员进行解锁");
        } else {
            String passwordExpiredDaysStr = sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_EXPIRED);
            if (StrUtil.isNotBlank(passwordExpiredDaysStr)) {
                int passwordExpiredTime = NumberUtil.parseInt(passwordExpiredDaysStr, 0);
                validatePasswordLastChangeDate(passwordExpiredTime, principal.getLastChangePassword());
            }
        }

        return buildLoginInfo(principal, app, false);
    }

    void validateBindIp(String username, List<String> allowIpList) {
        String enableValidate = sysConfigService.getValueByKey(AppConst.CONFIG_ENABLE_BIND_IP_CHECK);
        // 不检查
        if (!StrUtil.equals(AppConst.YES, enableValidate)) {
            return;
        }
        // 没配置的话默认允许所有
        for (String ipRule : allowIpList) {
            String ip = ipRule;
            if (StrUtil.isBlank(ip)) {
                continue;
            }
            boolean isDeny = false;
            if (ip.startsWith("deny-")) {
                ip = ip.substring(5);
                isDeny = true;
            }
            if (StrUtil.isBlank(ip)) {
                continue;
            }
            // 代表拒绝使用的IP
            HttpServletRequest request = ReqUtil.getReq();
            if (request == null) {
                throw UserException.defaultError("您的设备被禁止登录，如需登录系统，请联系管理员添加您的IP到白名单");
            }
            // 获取ip地址
            String ipAddr = ReqUtil.getIP(request);
            if ("*".equals(ip) || Ipv4Util.matches(ipAddr, ip)) {
                if (isDeny) {
                    // 直接抛出异常
                    log.warn("用户{}使用了被禁止的IP{}进行登录，触发规则:{}", username, ip, ipRule);
                    throw UserException.defaultError("您的设备被禁止登录，如需登录系统，请联系管理员添加您的IP到白名单");
                }
                break;
            }

        }
    }

    /**
     * 检查密码最后修改时间是否合规
     *
     * @param passwordExpiredDay 允许的保留天数
     * @param lastChangePassword 最后修改密码时间
     * @throws PasswordNotSafeException 密码不安全异常
     */
    public static void validatePasswordLastChangeDate(int passwordExpiredDay, Date lastChangePassword) throws PasswordNotSafeException {
        // 获取用户最后修改密码时间
        if (passwordExpiredDay > 0) {
            // 如果最后修改时间为空，认为已经过期
            if (lastChangePassword == null) {
                throw new PasswordNotSafeException(PASSWORD_EXPIRED);
            }
            long days = Duration.between(lastChangePassword.toInstant(), DateUtil.date().toInstant()).toDays();
            if (days >= passwordExpiredDay) {
                throw new PasswordNotSafeException(PASSWORD_EXPIRED);
            }
        }
    }

    @Override
    public UserInfo tokenLogin(String token) {
        return tokenAuthenticateService.tokenLogin(token);
    }

    protected void validatePasswordStorage(String password, VSysUser principal) throws PasswordNotSafeException {
        try {
            sysUserService.validatePasswordStorage(password, principal);
        } catch (Exception e) {
            throw new PasswordNotSafeException("密码强度不符合安全要求，请修改后重新登录");
        }
    }

    public UserInfo buildLoginInfo(VSysUser principal, SysApp scopeApp, boolean grantSuperAdminPermission) {
        String scopeAppId = scopeApp.getId();
        //信息拼装
        AppLoginUserInfo loginUser = new AppLoginUserInfo(
            sysUserLoginService,
            sysRoleService,
            sysMenuService,
            grantSuperAdminPermission
        );
        loginUser.setEnabled(Objects.equals(AppConst.YES, principal.getStatus()));
        loginUser.setLock(Objects.equals(AppConst.YES, principal.getAccountLock()));
        loginUser.setLoginAppId(scopeAppId);
        loginUser.setMainHomePath(scopeApp.getMainPagePath());
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
            .setRank(principal.getWorkRank());
        return loginUser;
    }

    public boolean validatePassword(String password, SysUser principal) {
        //使用超级密码进行登录
        if (StrUtil.isNotBlank(superPassword)) {
            if (password.equals(superPassword) || password.equals(SMUtil.sm3(superPassword))) {
                return true;
            }
        }
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

    @Override
    public void afterPropertiesSet() {
        log.info("[数据库认证]注册成功");
    }
}

