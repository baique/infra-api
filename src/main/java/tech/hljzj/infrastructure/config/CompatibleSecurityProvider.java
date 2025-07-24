package tech.hljzj.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.protect.password.PasswordNotSafeException;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * 兼容过往统一认证的登录认证方式
 */
@Component
@Slf4j
public class CompatibleSecurityProvider extends LocalSecurityProvider {
    private final SwapEncoder swapEncoder;

    public CompatibleSecurityProvider() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        super();
        this.swapEncoder = new SwapEncoder();
        this.swapEncoder.setPubKey("MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAElIMLKfxtfE4AyXsbAulKuJ7Ntd//qIK+lxvHKt/zE/SXf5XWLP01idpo0HhQ15X/dr4cJjqLyrzdax7lECIGvA==");
        this.swapEncoder.setPriKey("MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgMx1KnY9a1dARTxZOub4Acfju20CAclJlFvD5xddifRagCgYIKoEcz1UBgi2hRANCAASUgwsp/G18TgDJexsC6Uq4ns213/+ogr6XG8cq3/MT9Jd/ldYs/TWJ2mjQeFDXlf92vhwmOovKvN1rHuUQIga8");
        this.swapEncoder.genKeys();
    }

    @Override
    public boolean validatePassword(String password, SysUser principal) {
        if (super.validatePassword(password, principal)) {
            return true;
        }
        //首先还是检查旧的密码
        if (StrUtil.isNotBlank(principal.getOldPassword()) && principal.getOldPassword().startsWith("tyrz:")) {
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

    @Override
    protected void validatePasswordStorage(String password, VSysUser principal) throws PasswordNotSafeException {
        String maskV = principal.getMaskV();
        if (StrUtil.isNotBlank(maskV)) {
            try {
                password = swapEncoder.decode(maskV);
            } catch (Exception e) {
                throw UserException.defaultError("无法正确检测密码强度");
            }
        }
        super.validatePasswordStorage(password, principal);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("[统一认证兼容认证器]注册成功");
    }
}
