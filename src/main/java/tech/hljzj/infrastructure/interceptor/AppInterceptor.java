package tech.hljzj.infrastructure.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.interceptor.BaseInterceptor;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.framework.util.web.ReqUtil;
import tech.hljzj.infrastructure.config.AppLoginUserInfo;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.util.AppScopeHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用信息校验拦截
 */
@Slf4j
@Component
public class AppInterceptor implements BaseInterceptor {

    private final SysAppService sysAppService;

    public AppInterceptor(SysAppService sysAppService) {
        this.sysAppService = sysAppService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        String token = AppScopeHolder.getRawAppToken();
        if (StrUtil.isBlank(token)) {
            return true;
        }
        String id = AppScopeHolder.requiredScopeAppId();
        SysApp sysApp = sysAppService.getById(id);

        try {
            JWT.require(Algorithm.HMAC384(sysApp.getSecret())).build().verify(token);
            if (AuthUtil.isLogin()) {
                LoginUser loginUser = AuthUtil.getLoginUser();
                AppLoginUserInfo userInfo = (AppLoginUserInfo) loginUser.getUserInfo();
                String s = userInfo.getLoginAppId();
                if (!StrUtil.equals(s, id)) {
                    ReqUtil.writeResponse(response, R.fail().setMsg("非法的请求:用户会话与登录凭据不匹配"));
                }
            }
            return true;
        } catch (Exception e) {
            ReqUtil.writeResponse(response, R.fail().setMsg("非法的请求:无法正确解析请求客户端!"));
            return false;
        }
    }
}
