package tech.hljzj.infrastructure.service.mcp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysRoleMenu;
import tech.hljzj.infrastructure.service.SysRoleMenuService;

import java.util.List;

@Service
public class RoleMenuMcpService implements Mcp {
    private final SysRoleMenuService sysRoleMenuService;

    public RoleMenuMcpService(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Tool(name = "queryRoleMenuListCondition", description = """
        查询角色可以访问的菜单，不设置条件项的是查询所有(永远不允许执行查询所有)；返回信息的结构说明如下（同时是查询项列表）
         /**
             * bigint
             */
            @TableId(type = IdType.ASSIGN_UUID, value = "id_")
            private String id;
            /**
             * 角色标识
             */
            @TableField(value = "role_id_")
            private String roleId;
            /**
             * 应用标识
             */
            @TableField(value = "app_id_")
            private String appId;
            /**
             * 菜单标识
             */
            @TableField(value = "menu_id_")
            private String menuId;
        """)
    public List<SysRoleMenu> queryRoleListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") SysRoleMenu q) {
        return sysRoleMenuService.list(Wrappers.query(q));
    }
}
