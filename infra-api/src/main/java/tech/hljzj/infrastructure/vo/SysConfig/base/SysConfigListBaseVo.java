package tech.hljzj.infrastructure.vo.SysConfig.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysConfig;
import java.lang.*;
import java.util.Date;


/**
 * 系统配置 sys_config 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysConfigListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    @ExcelProperty(value = "bigint")
    private String id;
    /**
     * 所属应用标识
     */
    @ExcelProperty(value = "所属应用标识")
    private String ownerAppId;
    /**
     * 配置标识
     */
    @ExcelProperty(value = "配置标识")
    private String key;
    /**
     * 配置名称
     */
    @ExcelProperty(value = "配置名称")
    private String name;
    /**
     * 配置项值
     */
    @ExcelProperty(value = "配置项值")
    private String value;
    /**
     * 配置状态
     */
    @ExcelProperty(value = "配置状态")
    private String status;
    /**
     * 配置描述
     */
    @ExcelProperty(value = "配置描述")
    private String desc;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;
    /**
     * 是否锁定
     */
    @ExcelProperty(value = "是否锁定")
    private String locked;

    public <T extends SysConfigListBaseVo> T fromDto(SysConfig dto){
        this.setId(dto.getId());
        this.setOwnerAppId(dto.getOwnerAppId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setValue(dto.getValue());
        this.setStatus(dto.getStatus());
        this.setDesc(dto.getDesc());
        this.setSort(dto.getSort());
        this.setLocked(dto.getLocked());
        //noinspection unchecked
        return (T) this;
    }

    public SysConfig toDto(){
        SysConfig dto = new SysConfig();
        dto.setId(this.getId());
        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setValue(this.getValue());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setSort(this.getSort());
        dto.setLocked(this.getLocked());
        return dto;
    }
}