package tech.hljzj.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.exception.ServerException;
import tech.hljzj.framework.security.SecurityProvider;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.util.AppScopeHolder;

/**
 * 应用认证
 */
@Component
public class AppSecurityProvider implements SecurityProvider {
    private final SysAppService sysAppService;

    public AppSecurityProvider(SysAppService sysAppService) {
        this.sysAppService = sysAppService;
    }

    @Override
    public UserInfo login(Authentication authentication) {
        UserInfo userInfo = new UserInfo();
        Object principal = authentication.getPrincipal();
        if (StrUtil.isBlankIfStr(principal)) {
            throw new ServerException(411, "未知的应用凭据");
        }

        String appId = AppScopeHolder.getScopeAppId(principal.toString());
        SysApp app = sysAppService.entityGet(appId);
        if (app == null) {
            throw new ServerException(411, "未知的应用凭据");
        }

        if (!AppConst.YES.equals(app.getVerifyIp()) || StrUtil.isBlank(app.getTrustIp())) {
            throw new ServerException(411, "如需使用应用认证必须配置授信IP，否则请使用用户认证");
        }
        userInfo.setId("app:" + app.getId());
        userInfo.setAccount(app.getName());
        userInfo.setName(app.getName());

        userInfo.setEnabled(true);
        userInfo.setLock(false);

        return userInfo;
    }

    @Override
    public boolean independentVerification() {
        return false;
    }
}
