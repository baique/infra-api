package tech.hljzj.infrastructure.config;

import cn.hutool.core.lang.id.NanoId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 树形所需的code码生成
 */
@Component
public class TreeCodeGenerate {
    /**
     * 部门树
     */
    @Value("${tree-code.dept.length:16}")
    private int deptCodeLength;

    /**
     * 菜单树
     */
    @Value("${tree-code.menu.length:8}")
    private int menuCodeLength;

    public String newDeptCode() {
        return NanoId.randomNanoId(deptCodeLength);
    }

    public String newMenuCode() {
        return NanoId.randomNanoId(menuCodeLength);
    }
}
