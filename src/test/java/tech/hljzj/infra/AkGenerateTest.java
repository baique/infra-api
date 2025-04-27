package tech.hljzj.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Test;

public class AkGenerateTest {
    @Test
    public void generateAk() {
        System.out.println(JWT.create()
                .withKeyId("0")
                .withClaim("name", "基座服务")
                .sign(Algorithm.HMAC384("000000")));
    }
}
