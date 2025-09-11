package tech.hljzj.infrastructure.vo.SysDept;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.util.excel.dict.DictConvertHandle;
import tech.hljzj.framework.util.excel.dict.UseDict;

/**
 * 组织管理 sys_dept 
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
public class SysDeptImportVo {
    /**
     * 组织标识
     */
    @ExcelProperty(value = "组织编码")
    private String key;
    /**
     * 组织名称
     */
    @ExcelProperty(value = "组织名称")
    private String name;
    /**
     * 组织简称
     */
    @ExcelProperty(value = "组织简称")
    private String shortName;
    /**
     * 上级组织编码
     */
    @ExcelProperty(value = "上级组织编码")
    private String parentKey;
    /**
     * 上级组织
     */
    @ExcelProperty(value = "上级组织名称")
    private String parentName;

    @ExcelProperty(value = "组织别名")
    private String alias;

    @ExcelProperty(value = "组织职责")
    private String duty;

    @ExcelProperty(value = "组织类型", converter = DictConvertHandle.class)
    @UseDict("org_type")
    private String type;

    @ExcelProperty(value = "所在地点")
    private String addr;

    @ExcelProperty(value = "地点编码")
    private String addrNo;
}