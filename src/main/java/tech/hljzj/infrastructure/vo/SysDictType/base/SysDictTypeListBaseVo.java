package tech.hljzj.infrastructure.vo.SysDictType.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDictType;

import java.io.Serializable;


/**
 * 字典组 sys_dict_type_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictTypeListBaseVo implements Serializable {
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
     * 字典类型标识
     */
    @ExcelProperty(value = "字典类型标识")
    private String key;
    /**
     * 字典类型名称
     */
    @ExcelProperty(value = "字典类型名称")
    private String name;
    /**
     * 字典类型状态
     */
    @ExcelProperty(value = "字典类型状态")
    private String status;
    /**
     * 值长度限制
     */
    @ExcelProperty(value = "值长度限制")
    private Integer maxLength;
    /**
     * 字典说明
     */
    @ExcelProperty(value = "字典说明")
    private String helpMessage;
    /**
     * 字典类型描述
     */
    @ExcelProperty(value = "字典类型描述")
    private String desc;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;

    public <T extends SysDictTypeListBaseVo> T fromDto(SysDictType dto){
        this.setId(dto.getId());
        this.setOwnerAppId(dto.getOwnerAppId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setStatus(dto.getStatus());
        this.setMaxLength(dto.getMaxLength());
        this.setHelpMessage(dto.getHelpMessage());
        this.setDesc(dto.getDesc());
        this.setSort(dto.getSort());
        //noinspection unchecked
        return (T) this;
    }

    public SysDictType toDto(){
        SysDictType dto = new SysDictType();
        dto.setId(this.getId());
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