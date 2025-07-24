package tech.hljzj.infrastructure.vo.SysApp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;

/**
 * 应用管理 sys_app
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppDetailVo extends SysAppListVo {
    // 在这里可以扩展其他属性
    public String getAppToken() {
        return JWT.create()
                .withKeyId(this.getId())
                .withClaim("name", this.getName())
                .sign(Algorithm.HMAC384(this.getSecret()));
    }
}