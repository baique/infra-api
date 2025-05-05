package tech.hljzj.infrastructure.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.SysLogEntity;
import tech.hljzj.framework.security.handler.AppLoginFailHandler;
import tech.hljzj.framework.security.handler.AppLoginSuccessHandler;
import tech.hljzj.framework.service.ILoggerService;
import tech.hljzj.framework.util.web.ReqUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class AppLoginLogHandle {


    @Component
    @Primary
    public static class LogLoginSuccess extends AppLoginSuccessHandler {
        @Autowired
        private ILoggerService loggerService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                super.onAuthenticationSuccess(request, response, authentication);
            } finally {
                SysLogEntity sysLogEntity = loggerService.buildLog();
                sysLogEntity.setModuleName("登录");
                sysLogEntity.setOperType("登录");
                sysLogEntity.setOperContent("登录成功");
                loggerService.recordLog(sysLogEntity);
            }
        }
    }

    @Component
    @Primary
    public static class LogLoginFail extends AppLoginFailHandler {
        @Autowired
        private ILoggerService loggerService;

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            try {
                if (exception instanceof LocalSecurityProvider.PasswordExpiredException) {
                    //密码已经过期，需要修改后再登录
                    ReqUtil.writeResponse(response, R.fail().setCode(402).setMsg(exception.getMessage()));
                    return;
                }
                super.onAuthenticationFailure(request, response, exception);
            } catch (Exception x) {
                SysLogEntity sysLogEntity = loggerService.buildLog();
                sysLogEntity.setModuleName("登录");
                sysLogEntity.setOperType("登录");
                sysLogEntity.setOperContent("登录失败:" + ExceptionUtil.stacktraceToString(x, 1990));
                sysLogEntity.setErrorCode(500);
                loggerService.recordLog(sysLogEntity);
            }
        }
    }
}
