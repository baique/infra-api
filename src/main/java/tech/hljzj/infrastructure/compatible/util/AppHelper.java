package tech.hljzj.infrastructure.compatible.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.security.SessionStoreDecorator;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.infrastructure.compatible.vo.AppInfo;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.util.AppScopeHolder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AppHelper {
    public static final String TICKET_TOKEN_KEY = "TYRZTOKEN";
    @Autowired
    private SessionStoreDecorator storeDecorator;

    private static SessionStoreDecorator nameSessionRepository;


    public static String createTicket(LoginUser loginUser) {
        Algorithm al = Algorithm.HMAC256("tyrzxt");
        return JWT.create()
            .withIssuer(AppScopeHolder.requiredScopeAppId())
            .withClaim("ac", loginUser.getUserInfo().getAccount())
            .withClaim(TICKET_TOKEN_KEY, loginUser.getUserInfo().getAccount())
            .sign(al);
    }

    @Getter
    @Setter
    public static class Info {
        private String appId;
        private SysApp appInfo;
        private String token;
    }

    public static String getLoginApp(HttpServletRequest request) {
        String scopeAppId = AppScopeHolder.getScopeAppId();
        if (StrUtil.isNotBlank(scopeAppId)) {
            return scopeAppId;
        }
        String sign = getSign(request);
        DecodedJWT jwt = JWT.decode(sign);
        return jwt.getSubject();
    }

    public static Info getLoginInfo(HttpServletRequest request) {
        String sign = getSign(request);
        DecodedJWT jwt = JWT.decode(sign);
        Info f = new Info();
        f.setAppId(jwt.getSubject());
        f.setToken(jwt.getClaim("TYRZTOKEN").asString());
        return f;
    }

    public static Info getLoginInfo(String ticket) {
        DecodedJWT jwt = JWT.decode(ticket);
        Info f = new Info();
        f.setAppId(jwt.getSubject());
        f.setToken(jwt.getClaim("TYRZTOKEN").asString());
        return f;
    }

    public static String getSign(HttpServletRequest request) {
        return request.getParameter("sign");
    }

    public static LoginUser getLoginUser(HttpServletRequest request) {
        AppHelper.Info loginInfo = AppHelper.getLoginInfo(request);
        String token = loginInfo.getToken();

        //这里需要解析一下token
        Optional<TokenAuthentication> tokenAuth = nameSessionRepository.getUserByToken(token);
        return tokenAuth.map(TokenAuthentication::getPrincipal).orElse(null);

    }

    @PostConstruct
    void init() {
        nameSessionRepository = this.storeDecorator;
    }
}
