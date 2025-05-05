package tech.hljzj.infrastructure.compatible.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.store.JwtTokenStore;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AppHelper {
    @Autowired
    private JwtTokenStore _nameSessionRepository;

    private static JwtTokenStore nameSessionRepository;

    @Getter
    @Setter
    public static class Info {
        private String appId;
        private String token;
    }

    public static String getLoginApp(HttpServletRequest request) {
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

    public static Info getLoginInfo(String sign) {
        DecodedJWT jwt = JWT.decode(sign);
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
        nameSessionRepository = this._nameSessionRepository;
    }
}
