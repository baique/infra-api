package tech.hljzj.infrastructure.vo.SysDictData.base;

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
import tech.hljzj.infrastructure.domain.SysDictData;

import java.io.Serializable;


/**
 * 字典数据 sys_dict_data
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
public class SysDictDataListBaseVo implements Serializable {
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
     * 所属字典类型标识
     */
    private String ownerTypeId;
    /**
     * 字典标识
     */
    @ExcelProperty(value = "字典标识")
    private String key;
    /**
     * 字典名称
     */
    @ExcelProperty(value = "字典名称")
    private String name;
    /**
     * 字典项值
     */
    @ExcelProperty(value = "字典项值")
    private String value;
    /**
     * 数据样式
     */
    @ExcelProperty(value = "数据样式")
    private String listClass;
    /**
     * 是否可选
     */
    @ExcelProperty(value = "是否可选", converter = DictConvertHandle.class)
    @UseDict("yes_no")
    private String selectable;
    /**
     * 字典状态
     */
    @ExcelProperty(value = "字典状态",converter = DictConvertHandle.class)
    @UseDict("status")
    private String status;
    /**
     * 选项说明
     */
    @ExcelProperty(value = "选项说明")
    private String helpMessage;
    /**
     * 字典描述
     */
    @ExcelProperty(value = "字典描述")
    private String desc;
    /**
     * 是否锁定
     */
    @ExcelProperty(value = "是否锁定",converter = DictConvertHandle.class)
    @UseDict("yes_no")
    private String locked;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;

    public <T extends SysDictDataListBaseVo> T fromDto(SysDictData dto) {
        this.setId(dto.getId());
        this.setOwnerAppId(dto.getOwnerAppId());
        this.setOwnerTypeId(dto.getOwnerTypeId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setValue(dto.getValue());
        this.setListClass(dto.getListClass());
        this.setSelectable(dto.getSelectable());
        this.setStatus(dto.getStatus());
        this.setHelpMessage(dto.getHelpMessage());
        this.setDesc(dto.getDesc());
        this.setLocked(dto.getLocked());
        this.setSort(dto.getSort());
        //noinspection unchecked
        return (T) this;
    }

    public SysDictData toDto() {
        SysDictData dto = new SysDictData();
        dto.setId(this.getId());
        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setOwnerTypeId(this.getOwnerTypeId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setValue(this.getValue());
        dto.setListClass(this.getListClass());
        dto.setSelectable(this.getSelectable());
        dto.setStatus(this.getStatus());
        dto.setHelpMessage(this.getHelpMessage());
        dto.setDesc(this.getDesc());
        dto.setLocked(this.getLocked());
        dto.setSort(this.getSort());
        return dto;
    }
}