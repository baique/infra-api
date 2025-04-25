package tech.hljzj.infrastructure.vo.SysUser;


import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysApp;

import java.util.Date;

@Getter
@Setter
public class TokenInfoVo {
    /**
     * token登录系统
     */
    private SysApp loginApp;
    /**
     * token值
     */
    private String tokenValue;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 上次活跃时间
     */
    private Date lastActiveTime;
    /**
     * 使用终端
     */
    private String useAgent;

}
