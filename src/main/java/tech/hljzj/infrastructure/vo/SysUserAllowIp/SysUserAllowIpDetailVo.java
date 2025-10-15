package tech.hljzj.infrastructure.vo.SysUserAllowIp;

import lombok.Getter;
import lombok.Setter;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
@HeadRowHeight(30)
@ContentRowHeight(15)
@ColumnWidth(18)
@ContentFontStyle(fontHeightInPoints = 12)
@ExcelIgnoreUnannotated
public class SysUserAllowIpDetailVo extends  SysUserAllowIpListVo {
  // 在这里可以扩展其他属性
}