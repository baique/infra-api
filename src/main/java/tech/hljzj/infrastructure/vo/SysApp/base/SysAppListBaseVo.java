package tech.hljzj.infrastructure.vo.SysApp.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysApp;

import java.io.Serializable;


/**
 * 应用管理 sys_app_
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppListBaseVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    private String id;
    /**
     * 应用标识
     */
    @ExcelProperty(value = "应用标识")
    private String key;
    /**
     * 应用名称
     */
    @ExcelProperty(value = "应用名称")
    private String name;
    /**
     * 主页地址
     */
    @ExcelProperty(value = "主页地址")
    private String mainPagePath;
    /**
     * 应用密钥
     */
    @ExcelProperty(value = "应用密钥")
    private String secret;
    /**
     * 应用状态
     */
    @ExcelProperty(value = "应用状态")
    private String status;
    /**
     * 应用描述
     */
    @ExcelProperty(value = "应用描述")
    private String desc;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 是否校验授信IP
     */
    @ExcelProperty(value = "是否校验授信IP")
    private String verifyIp;
        /**
     * 信任IP
     */
    @ExcelProperty(value = "信任IP")
    private String trustIp;

    public <T extends SysAppListBaseVo> T fromDto(SysApp dto) {
        this.setId(dto.getId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setMainPagePath(dto.getMainPagePath());
        this.setSecret(dto.getSecret());
        this.setStatus(dto.getStatus());
        this.setDesc(dto.getDesc());
        this.setSort(dto.getSort());
        this.setVerifyIp(dto.getVerifyIp());
        this.setTrustIp(dto.getTrustIp());
        //noinspection unchecked
        return (T) this;
    }

    public SysApp toDto() {
        SysApp dto = new SysApp();
        dto.setId(this.getId());
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