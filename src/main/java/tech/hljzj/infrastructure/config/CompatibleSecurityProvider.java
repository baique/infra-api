package tech.hljzj.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;
import tech.hljzj.infrastructure.domain.SysUser;

/**
 * 兼容过往统一认证的登录认证方式
 */
@Component
public class CompatibleSecurityProvider extends LocalSecurityProvider {
    private final SwapEncoder swapEncoder;

    public CompatibleSecurityProvider(SwapEncoder swapEncoder) {
        super();
        this.swapEncoder = swapEncoder;
    }

    @Override
    public boolean validatePassword(String password, SysUser principal) {
        if (super.validatePassword(password, principal)) {
            return true;
        }
        //这里升级一下，兼容统一认证的密码校验方式
        String maskV = principal.getMaskV();
        if (StrUtil.isBlank(maskV)) {
            return false;
        }
        try {
            String decode = swapEncoder.decode(maskV);
            //使用sha512加盐加密
            String d = DigestUtil.sha512Hex(decode + "tyrz");
            return d.equals(password);
        } catch (Exception e) {
            return false;
        }


    }
}
