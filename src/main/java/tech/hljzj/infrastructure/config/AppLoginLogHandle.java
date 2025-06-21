package tech.hljzj.infrastructure.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import tech.hljzj.framework.logger.LogEvent;
import tech.hljzj.framework.logger.SysLogEntity;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.handler.On;
import tech.hljzj.framework.service.ILoggerService;

@Slf4j
@Configuration
public class AppLoginLogHandle {
    @Autowired
    private ILoggerService loggerService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener(On.LoginSuccess.class)
    public void onSuccess(On.LoginSuccess event) {
        SysLogEntity sysLogEntity = loggerService.createBaseEntity();
        sysLogEntity.setModuleName("登录");
        sysLogEntity.setOperType("登录");
        LoginUser principal = event.getPrincipal();
        sysLogEntity.setOperContent(principal.getUserInfo().getAccount() + "登录成功");
        applicationEventPublisher.publishEvent(new LogEvent(sysLogEntity));
    }

    @EventListener(On.LoginFail.class)
    public void onSuccess(On.LoginFail event) {
        SysLogEntity sysLogEntity = loggerService.createBaseEntity();
        sysLogEntity.setModuleName("登录");
        sysLogEntity.setOperType("登录");
        sysLogEntity.setOperContent("登录失败:" + ExceptionUtil.stacktraceToString(event.getAuthenticationException(), 1990));
        sysLogEntity.setErrorCode(500);
        applicationEventPublisher.publishEvent(new LogEvent(sysLogEntity));
    }
}
