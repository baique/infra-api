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
        //首先还是检查旧的密码
        if (principal.getOldPassword().startsWith("tyrz:")) {
            //但这一次是直接使用用户输入的密码和加密后比较
            if (password.equals(principal.getOldPassword().substring(5))) {
                return true;
            }
        }
        //兼容统一认证的密码校验方式
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
