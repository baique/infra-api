package tech.hljzj.infrastructure.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.cache.CommonCache;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysUser;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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

    public int incrLoginFail(String username){
        String key = "login:fail:" + username;
        Object v = commonCache.get(key);
        int prevFailCount = 0;
        if (!StrUtil.isBlankIfStr(v)) {
            prevFailCount = Integer.parseInt(v.toString());
        }
        prevFailCount += 1;
        // 如果用户持续失败
        commonCache.put(key, prevFailCount, 1L, TimeUnit.HOURS);
        return prevFailCount;
    }

    public void resetLoginFail(String username){
        String key = "login:fail:" + username;
        commonCache.delete(key);
    }

    public void lock(String username) {
        String key = "login:lock:" + username;
        // 用当前时间
        Duration duration = null;
        try {
            String d = sysConfigService.getValueByKey(AppConst.CONFIG_AUTO_UNLOCK);
            duration = DurationStyle.detectAndParse(d).abs();
        } catch (Exception e) {
//            duration = DurationStyle.detectAndParse("10m");
            log.warn("由于系统配置的自动解锁时间有误，此处直接禁用用户");
        }
        this.resetLoginFail(username);
        if (duration != null) {
            // 将账号锁定放入缓存，到期自动解锁，如果缓存本地化会随着重启而解锁
            commonCache.put(key, 1, duration.toMillis(), TimeUnit.MILLISECONDS);
            commonCache.put(key, 1, duration.toMillis(), TimeUnit.MILLISECONDS);
            //这里用户真实的状态没有被改变，只是一个虚拟的锁
        } else {
            //无法自动解锁
            commonCache.put(key, 1);
            // 将用户锁定，同时将客户端锁定
            sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                    .set(SysUser::getAccountLock, AppConst.YES)
                    .eq(SysUser::getUsername, username)
            );
        }
    }

    public void unLock(String username) {
        String key = "login:lock:" + username;
        commonCache.delete(key);
        this.resetLoginFail(username);
        // 同时解锁用户状态
        sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getAccountLock, AppConst.NOT)
                .eq(SysUser::getUsername, username)
        );
    }
}
