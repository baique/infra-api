package tech.hljzj.infrastructure.vo.SysMenu;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.vo.SysMenu.base.SysMenuListBaseVo;

/**
 * 菜单管理 sys_menu
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
@ExcelIgnoreUnannotated
@HeadRowHeight(30)
@ContentRowHeight(15)
@ColumnWidth(18)
@ContentFontStyle(fontHeightInPoints = (short) 12)
public class SysMenuListVo extends SysMenuListBaseVo {
    // 在这里可以扩展其他属性
    @ExcelProperty(value = "上级菜单")
    private String parentKey;
}