package tech.hljzj.infrastructure.vo.SysUserAllowIp.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserAllowIpNewBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 绑定IP
     */
    private String allowIp;

    /**
     * 转换为实际的数据实体
     */
    public SysUserAllowIp toDto(){
        SysUserAllowIp dto = new SysUserAllowIp();
          
        dto.setUserId(this.getUserId());
        dto.setAllowIp(this.getAllowIp());

        return dto;
    }
}