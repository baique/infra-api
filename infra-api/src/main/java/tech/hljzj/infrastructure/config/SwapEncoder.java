package tech.hljzj.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.util.password.SMUtil;

import javax.annotation.PostConstruct;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 数据交换加密
 */
@Component
@Getter
@Setter
public class SwapEncoder {
    /**
     * 公演
     */
    @Value("${swap.pubKey}")
    private String pubKey;
    /**
     * 私钥
     */
    @Value("${swap.priKey}")
    private String priKey;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public String encode(String dataStr) throws Exception {
        return SMUtil.sm2Encrypt(dataStr, publicKey);
    }

    public String decode(String dataStr) throws Exception {
        return SMUtil.sm2Decrypt(dataStr, privateKey);
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @PostConstruct
    void genKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        byte[] pubKeyByte = Base64.getDecoder().decode(pubKey);
        byte[] priKeyByte = Base64.getDecoder().decode(priKey);
        KeyFactory bc = KeyFactory.getInstance("EC", "BC");
        this.publicKey = bc.generatePublic(new X509EncodedKeySpec(pubKeyByte));
        this.privateKey = bc.generatePrivate(new PKCS8EncodedKeySpec(priKeyByte));
    }
}
