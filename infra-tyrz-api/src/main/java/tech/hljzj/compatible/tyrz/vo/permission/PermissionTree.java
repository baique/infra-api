package tech.hljzj.compatible.tyrz.vo.permission;

import tech.hljzj.framework.util.tree.TreeUtil;
import tech.hljzj.infrastructure.domain.SysMenu;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionTree {
    public static List<Permission> from(List<SysMenu> menus) {
        return TreeUtil.listToTree(menus.stream().map(Permission::from).collect(Collectors.toList()));
    }
}
