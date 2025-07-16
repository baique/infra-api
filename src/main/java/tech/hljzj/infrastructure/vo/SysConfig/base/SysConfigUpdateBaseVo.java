package tech.hljzj.infrastructure.vo.SysConfig.base;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysConfig;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 系统配置 sys_config 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysConfigUpdateBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @NotBlank
    private String id;
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
    private Map<String, Object> attribution;

    /**
     * 转换为实际的数据实体
     */
    public SysConfig toDto() {
        SysConfig dto = new SysConfig();

        dto.setId(this.getId());
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