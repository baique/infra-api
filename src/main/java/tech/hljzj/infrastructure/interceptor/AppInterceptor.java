package tech.hljzj.infrastructure.interceptor;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.interceptor.BaseInterceptor;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.framework.util.web.ReqUtil;
import tech.hljzj.infrastructure.code.AppConst;
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


    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
        "/token",
        "POST"
    );
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER2 = new AntPathRequestMatcher(
        "/authenticate",
        "POST"
    );

    private static final AntPathRequestMatcher GET_TOKEN_REQUEST_MATCHER = new AntPathRequestMatcher(
        "/tyrz/getToken",
        "POST"
    );


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String token = AppScopeHolder.getRawAppToken();
        if (StrUtil.isBlank(token)) {
            return true;
        }
        String id = AppScopeHolder.requiredScopeAppId();
        SysApp sysApp = sysAppService.entityGet(id);

        try {
            JWT.require(Algorithm.HMAC384(sysApp.getSecret())).build().verify(token);

            if (AppConst.YES.equals(sysApp.getVerifyIp()) && StrUtil.isNotBlank(sysApp.getTrustIp())) {
                // 检查访问IP
                String ipAddress = ReqUtil.getIP(request);

                if (StrUtil.startWithIgnoreCase(ipAddress, "0:0:0:0:0:0:0:1")) {
                    return true;
                }

                String[] allowIps = sysApp.getTrustIp().split("\n");
                for (String ip : allowIps) {
                    ip = StrUtil.trim(ip);
                    if (Ipv4Util.matches(ip, ipAddress)) {
                        return true;
                    }
                }
                ReqUtil.writeResponse(response, R.fail().setMsg("非法的请求:" + ipAddress + "不被信任"));
                return false;
            }

            if (!AuthUtil.isLogin()) {
                return true;
            }
            if (isCrossSystemLogin(request)) {
                return true;
            }
            LoginUser loginUser = AuthUtil.getLoginUser();
            UserInfo info = loginUser.getUserInfo();
            if (info instanceof AppLoginUserInfo) {
                AppLoginUserInfo userInfo = (AppLoginUserInfo) info;
                String s = userInfo.getLoginAppId();
                if (!StrUtil.equals(s, id)) {
                    ReqUtil.writeResponse(response, R.fail().setMsg("非法的请求:用户会话与登录凭据不匹配"));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            ReqUtil.writeResponse(response, R.fail().setMsg("非法的请求:无法正确解析请求客户端!"));
            return false;
        }
    }

    private static boolean isCrossSystemLogin(HttpServletRequest request) {
        return DEFAULT_ANT_PATH_REQUEST_MATCHER.matcher(request).isMatch()
            || GET_TOKEN_REQUEST_MATCHER.matcher(request).isMatch()
            || DEFAULT_ANT_PATH_REQUEST_MATCHER2.matcher(request).isMatch();
    }
}
