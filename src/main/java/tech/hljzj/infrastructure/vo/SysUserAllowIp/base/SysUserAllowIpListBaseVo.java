package tech.hljzj.infrastructure.vo.SysUserAllowIp.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import java.lang.*;
import java.util.Date;


/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserAllowIpListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 用户标识
     */
    @ExcelProperty(value = "用户标识")
    private String userId;
    /**
     * 绑定IP
     */
    @ExcelProperty(value = "绑定IP")
    private String allowIp;

    public <T extends SysUserAllowIpListBaseVo> T fromDto(SysUserAllowIp dto){
        this.setId(dto.getId());
        this.setUserId(dto.getUserId());
        this.setAllowIp(dto.getAllowIp());
        //noinspection unchecked
        return (T) this;
    }

    public SysUserAllowIp toDto(){
        SysUserAllowIp dto = new SysUserAllowIp();
        dto.setId(this.getId());
        dto.setUserId(this.getUserId());
        dto.setAllowIp(this.getAllowIp());
        return dto;
    }
}