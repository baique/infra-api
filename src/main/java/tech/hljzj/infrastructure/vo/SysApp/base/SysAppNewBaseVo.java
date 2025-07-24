package tech.hljzj.infrastructure.vo.SysApp.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

import tech.hljzj.infrastructure.domain.SysApp;

import javax.validation.constraints.NotBlank;

/**
 * 应用管理 sys_app 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 应用标识
     */
    @NotBlank
    private String key;
    /**
     * 应用名称
     */
    @NotBlank
    private String name;
    /**
     * 主页地址
     */
    private String mainPagePath;
    /**
     * 应用密钥
     */
    @NotBlank
    private String secret;
    /**
     * 应用状态
     */
    @NotBlank
    private String status;
    /**
     * 应用描述
     */
    private String desc;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 是否校验授信IP
     */
    private String verifyIp;
    /**
     * 信任IP
     */
    private String trustIp;

    /**
     * 转换为实际的数据实体
     */
    public SysApp toDto(){
        SysApp dto = new SysApp();
          
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setMainPagePath(this.getMainPagePath());
        dto.setSecret(this.getSecret());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setSort(this.getSort());
        dto.setVerifyIp(this.getVerifyIp());
        dto.setTrustIp(this.getTrustIp());

        return dto;
    }
}