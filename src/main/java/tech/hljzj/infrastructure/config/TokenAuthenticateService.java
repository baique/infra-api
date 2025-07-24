package tech.hljzj.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.BasicAuthenticationManager;
import tech.hljzj.framework.security.SecurityProvider;
import tech.hljzj.framework.security.SessionStoreDecorator;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.util.AppScopeHolder;

import java.util.Optional;

@Service
public class TokenAuthenticateService implements SecurityProvider {

    private final SessionStoreDecorator sessionStoreDecorator;
    private final SysUserService sysUserService;
    private final LocalSecurityProvider localSecurityProvider;
    private final SysAppService sysAppService;

    @Autowired
    @Lazy
    private BasicAuthenticationManager basicAuthenticationManager;

    public TokenAuthenticateService(SessionStoreDecorator sessionStoreDecorator, SysUserService sysUserService, LocalSecurityProvider localSecurityProvider, SysAppService sysAppService) {
        this.sessionStoreDecorator = sessionStoreDecorator;
        this.sysUserService = sysUserService;
        this.localSecurityProvider = localSecurityProvider;
        this.sysAppService = sysAppService;
    }

    @Override
    public tech.hljzj.framework.security.bean.UserInfo login(Authentication authentication) {
        String token = (String) authentication.getPrincipal();
        return tokenAuth(token, false);
    }

    @Override
    public UserInfo tokenLogin(String token) {
        return this.tokenAuth(token, false);
    }

    public UserInfo tokenLogin(String token, boolean grantSuperAdminPermission) {
        return this.tokenAuth(token, grantSuperAdminPermission);
    }

    private UserInfo tokenAuth(String token, boolean grantSuperAdminPermission) {
        Optional<TokenAuthentication> user = sessionStoreDecorator.getUserByToken(token);
        if (user.isEmpty()) {
            throw UserException.defaultError("登录动作执行失败,因为传入了一个无效的会话凭据");
        }

        TokenAuthentication existsAuthToken = user.get();
        AppLoginUserInfo u = (AppLoginUserInfo) existsAuthToken.getPrincipal().getUserInfo();

        return localSecurityProvider.buildLoginInfo(
            sysUserService.entityGet(u.getId(), true),
            sysAppService.entityGet(AppScopeHolder.requiredScopeAppId()),
            grantSuperAdminPermission
        );
    }

    @Override
    public boolean independentVerification() {
        return false;
    }

    public TokenAuthentication authenticate(String token) throws Exception {
        return this.basicAuthenticationManager.afterAuthenticate(this.tokenLogin(token), this);
    }

    public TokenAuthentication authenticate(String token, boolean grantSuperAdminPermission) throws Exception {
        return this.basicAuthenticationManager.afterAuthenticate(this.tokenLogin(token, grantSuperAdminPermission), this);
    }
}
