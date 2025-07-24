package tech.hljzj.infrastructure.compatible.vo.user;

import java.io.Serializable;

/**
 * 用户凭据类
 * 版本：1.0
 * 日期：2019年4月3日
 * 署名：王澳
 */
public class Credentials  implements Serializable {
    private final String token;
    private final String ticket;
    public Credentials(String token, String ticket){
        this.token = token;
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }
    public String getTicket() {
        return ticket;
    }
}
