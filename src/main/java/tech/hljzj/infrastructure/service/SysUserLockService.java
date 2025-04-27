package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.cache.CommonCache;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysUser;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class SysUserLockService {
    private final SysUserService sysUserService;
    private final CommonCache commonCache;
    private final SysConfigService sysConfigService;

    public SysUserLockService(SysUserService sysUserService, CommonCache commonCache, SysConfigService sysConfigService) {
        this.sysUserService = sysUserService;
        this.commonCache = commonCache;
        this.sysConfigService = sysConfigService;
    }


    public boolean isLocked(String username) {
        String key = "login:lock:" + username;
        return commonCache.hasKey(key);
    }

    public void lock(String username) {
        String key = "login:lock:" + username;
        // 将用户锁定，同时将客户端锁定
        sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getAccountLock, AppConst.YES)
                .eq(SysUser::getUsername, username)
        );
        // 用当前时间
        Duration duration;
        try {
            String d = sysConfigService.getValueByKey(AppConst.CONFIG_AUTO_UNLOCK);
            duration = DurationStyle.detectAndParse(d).abs();
        } catch (Exception e) {
            duration = DurationStyle.detectAndParse("2h");
            log.warn("由于系统配置的自动解锁时间有误，此处默认设为两小时");
        }
        Instant unlockInstant = Instant.now().plus(duration);
        Date unlockDate = Date.from(unlockInstant);
        // 将账号锁定放入缓存，如果缓存本地化会随着重启而解锁呢
        commonCache.put(key, unlockDate);
    }

    public void unLock(String username) {
        String key = "login:lock:" + username;
        commonCache.delete(key);
        // 同时解锁用户状态
        sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getAccountLock, AppConst.NOT)
                .eq(SysUser::getUsername, username)
        );
    }
}
