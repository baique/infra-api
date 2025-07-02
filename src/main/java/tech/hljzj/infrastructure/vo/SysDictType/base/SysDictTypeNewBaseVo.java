package tech.hljzj.infrastructure.vo.SysDictType.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysDictType;

import javax.validation.constraints.NotBlank;

/**
 * 字典类型 sys_dict_type 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictTypeNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 所属应用标识
     */
    @NotBlank
    private String ownerAppId;
    /**
     * 字典类型标识
     */
    @NotBlank
    private String key;
    /**
     * 字典类型名称
     */
    @NotBlank
    private String name;
    /**
     * 字典类型状态
     */
    private String status;
    /**
     * 值长度限制
     */
    private Integer maxLength;
    /**
     * 字典说明
     */
    private String helpMessage;
    /**
     * 字典类型描述
     */
    private String desc;
    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 转换为实际的数据实体
     */
    public SysDictType toDto(){
        SysDictType dto = new SysDictType();
          
        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setStatus(this.getStatus());
        dto.setMaxLength(this.getMaxLength());
        dto.setHelpMessage(this.getHelpMessage());
        dto.setDesc(this.getDesc());
        dto.setSort(this.getSort());

        return dto;
    }
}