package tech.hljzj.infrastructure.vo.SysConfig.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import tech.hljzj.infrastructure.domain.SysConfig;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置 sys_config 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysConfigNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 所属应用标识
     */
    @NotBlank
    private String ownerAppId;
    /**
     * 配置标识
     */
    @NotBlank
    private String key;
    /**
     * 配置名称
     */
    @NotBlank
    private String name;
    /**
     * 配置项值
     */
    private String value;
    /**
     * 配置状态
     */
    @NotBlank
    private String status;
    /**
     * 配置描述
     */
    private String desc;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 是否锁定
     */
    private String locked;
    /**
     * 扩展属性
     */
    private Map<String,Object> attribution;

    /**
     * 转换为实际的数据实体
     */
    public SysConfig toDto(){
        SysConfig dto = new SysConfig();
          
        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setValue(this.getValue());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setSort(this.getSort());
        dto.setLocked(this.getLocked());
        dto.setAttribution(this.getAttribution());

        return dto;
    }
}