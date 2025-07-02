package tech.hljzj.infrastructure.controller;

import com.nulabinc.zxcvbn.Strength;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.util.password.ParamEncryption;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.util.UserPasswordContext;
import tech.hljzj.protect.password.PasswordScorer;

/**
 * 工具接口
 */
@RestController
@RequestMapping("infrastructure/common")
public class CommonController extends BaseController {

    private final ParamEncryption paramEncryption;

    public CommonController(ParamEncryption paramEncryption) {
        this.paramEncryption = paramEncryption;
    }

    /**
     * 检查密码复杂度
     * @param password 密码
     * @param user 与密码相关的用户信息
     * @return 复杂度
     */
    @PostMapping("password-score")
    public R<Number> passwordScore(String password, @RequestBody SysUser user) {
        String decrypt = paramEncryption.decrypt(password);
        Strength sl = PasswordScorer.getPasswordStrengthScore(decrypt, UserPasswordContext.getContext(user));
        return R.ok(sl.getScore());
    }
}
