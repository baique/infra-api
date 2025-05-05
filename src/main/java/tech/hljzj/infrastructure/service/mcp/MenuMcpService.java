package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.service.SysMenuService;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.util.List;

@Service
public class MenuMcpService implements Mcp {
    private final SysMenuService sysMenuService;

    public MenuMcpService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Tool(name = "queryMenuList", description = "查询所有菜单")
    public List<SysMenu> queryMenuList() {
        SysMenuQueryVo q = new SysMenuQueryVo();
//        q.setMenuname(username);
        return sysMenuService.list(q);
    }

    @Tool(name = "queryMenuListCondition", description = """
        菜单，同时也是权限。
        菜单属于应用；
        菜单可以分配给角色，与角色之间是多对多关系；
        菜单本身实现了路径检索树，通过node_key和node_path字段完成，node_path的前缀检索是包含当前节点的；
        通过某些条件查询应用，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
          /**
             * bigint
             */
            @TableId(type = IdType.ASSIGN_UUID, value = "id_")
            private String id;
            /**
             * 所属应用标识
             */
            @TableField(value = "owner_app_id_")
            private String ownerAppId;
            /**
             * 节点标识
             */
            @TableField(value = "node_key_")
            private String nodeKey;
            /**
             * 节点路径
             */
            @TableField(value = "node_path_")
            private String nodePath;
            /**
             * 菜单标识
             */
            @TableField(value = "key_")
            private String key;
            /**
             * 菜单名称
             */
            @TableField(value = "name_")
            private String name;
            /**
             * 菜单状态
             */
            @TableField(value = "status_")
            private String status;
            /**
             * 菜单描述
             */
            @TableField(value = "desc_")
            private String desc;
            /**
             * 默认授予登录用户
             */
            @TableField(value = "default_grant_")
            private String defaultGrant;
            /**
             * 显示状态
             */
            @TableField(value = "visible_")
            private String visible;
            /**
             * 菜单类型
             */
            @TableField(value = "menu_type_")
            private String menuType;
            /**
             * 访问路径
             */
            @TableField(value = "path_")
            private String path;
            /**
             * 引用组件
             */
            @TableField(value = "component_")
            private String component;
            /**
             * 菜单图标
             */
            @TableField(value = "icon_")
            private String icon;
            /**
             * 标签
             */
            @TableField(value = "tag_")
            private String tag;
            /**
             * 类型
             */
            @TableField(value = "classes_")
            private String classes;
            /**
             * 排序编号
             */
            @TableField(value = "sort_")
            private Integer sort;
            /**
             * 上级权限标识
             */
            @TableField(value = "parent_id_")
            private String parentId;
        """)
    public List<SysMenu> queryMenuListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") SysMenuQueryVo q) {
        q.setEnablePage(Boolean.FALSE);
        return sysMenuService.list(q);
    }
}
