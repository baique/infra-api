package tech.hljzj.infrastructure.vo.SysDept;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.vo.SysDept.base.SysDeptListBaseVo;

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
@ExcelIgnoreUnannotated
public class SysDeptListVo extends SysDeptListBaseVo {
    // 在这里可以扩展其他属性
}