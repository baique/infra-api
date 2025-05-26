package tech.hljzj.infrastructure.controller.features;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.util.password.ParamEncryption;
import tech.hljzj.infrastructure.config.LocalSecurityProvider;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.service.SysUserService;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 签发证书功能
 *
 * @author zzg
 */
@Setter
@Getter
@Slf4j
@RestController()
@RequestMapping("/feat/key")
public class KeyAction extends BaseController {

    private final ParamEncryption paramEncryption;
    private final SysUserService sysUserService;
    private final LocalSecurityProvider localSecurityProvider;
    /**
     * 公钥
     */
    @Value("${feat.key.sync.PubKey}")
    private String syncPubKey;

    /**
     * 私钥
     */
    @Value("${feat.key.sync.PriKey}")
    private String syncPriKey;

    public KeyAction(ParamEncryption paramEncryption, SysUserService sysUserService, LocalSecurityProvider localSecurityProvider) {
        this.paramEncryption = paramEncryption;
        this.sysUserService = sysUserService;
        this.localSecurityProvider = localSecurityProvider;
    }

    /**
     * 公钥
     */
    @PostMapping("/getPubKey")
    @ResponseBody
    public Object getKey() {
        Map<String, String> map = new HashMap<>();
        map.put("spubx", getSyncPubKey().substring(0, 64).toUpperCase());
        map.put("spuby", getSyncPubKey().substring(64).toUpperCase());


        KeyPair keyPair = createECKeyPair();

        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        String pubHex = Hex.toHexString(publicKey.getQ().getEncoded(false));

        map.put("cpubx", pubHex.substring(2, 66).toUpperCase());
        map.put("cpuby", pubHex.substring(66).toUpperCase());
        map.put("cpri", privateKey.getD().toString(16).toUpperCase());

        return R.ok(map);
    }

    /**
     * 私钥
     */
    @PostMapping("/getPriKey")
    @ResponseBody
    public Object getPriKey() {

        KeyPair keyPair = createECKeyPair();

        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

        String pubHex = Hex.toHexString(publicKey.getQ().getEncoded(false));

        Map<String, String> map = new HashMap<>();
        map.put("spubx", pubHex.substring(2, 66));
        map.put("spuby", pubHex.substring(66));

        return R.ok(map);
    }

    /**
     * 签发证书前的密码校验
     *
     * @param ui 用户标识
     * @param en 密码
     * @return 错误信息
     */
    @RequestMapping("validate")
    public Object validateUserPassword(String ui, String en) {
        String rawPassword = paramEncryption.decrypt(en);
        SysUser user = sysUserService.getById(ui);
        if (user == null) {
            log.warn("被核验的用户不存在");
            throw UserException.defaultError("核验失败");
        } else {
            boolean b = localSecurityProvider.validatePassword(rawPassword, user);
            if (!b) throw UserException.defaultError("核验失败");
        }
        return R.ok();
    }


    /**
     * @return KeyPair
     */
    public static KeyPair createECKeyPair() {
        //使用标准名称创建EC参数生成的参数规范
        final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");

        // 获取一个椭圆曲线类型的密钥对生成器
        final KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            // 使用SM2算法域参数集初始化密钥生成器（默认使用以最高优先级安装的提供者的 SecureRandom 的实现作为随机源）
            // kpg.initialize(sm2Spec);

            // 使用SM2的算法域参数集和指定的随机源初始化密钥生成器
            kpg.initialize(sm2Spec, new SecureRandom());

            // 通过密钥生成器生成密钥对
            return kpg.generateKeyPair();
        } catch (Exception e) {
            log.error("SM key生成失败", e);
            throw UserException.defaultError("密钥对生成失败，请联系管理成员.");
        }
    }
}