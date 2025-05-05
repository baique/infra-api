package tech.hljzj.infrastructure.vo.SysDictType.base;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.util.excel.dict.DictConvertHandle;
import tech.hljzj.framework.util.excel.dict.UseDict;
import tech.hljzj.infrastructure.domain.SysDictType;


import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;


/**
 * 字典组 sys_dict_type_
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
@HeadRowHeight(30)
@ContentRowHeight(15)
@ColumnWidth(18)
@ContentFontStyle(fontHeightInPoints = (short) 12)
@ExcelIgnoreUnannotated
public class SysDictTypeListBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    private String id;
    /**
     * 所属应用标识
     */
    private String ownerAppId;
    /**
     * 字典项标识
     */
    @ExcelProperty(value = "字典项标识")
    @NotBlank
    private String key;
    /**
     * 字典项名称
     */
    @ExcelProperty(value = "字典项名称")
    @NotBlank
    private String name;
    /**
     * 字典项状态
     */
    @ExcelProperty(value = "字典项状态", converter = DictConvertHandle.class)
    @UseDict("status")
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
    @ColumnWidth(30)
    private String helpMessage;
    /**
     * 字典项描述
     */
    @ExcelProperty(value = "字典项描述")
    @ColumnWidth(30)
    private String desc;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;

    public <T extends SysDictTypeListBaseVo> T fromDto(SysDictType dto) {
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

    public SysDictType toDto() {
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