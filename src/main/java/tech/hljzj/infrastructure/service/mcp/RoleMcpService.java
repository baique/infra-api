package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;

import java.util.List;

@Service
public class RoleMcpService implements Mcp {
    private final SysRoleService sysRoleService;

    public RoleMcpService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }


    @Tool(name = "queryRoleListCondition", description = """
        角色属于应用，应用和角色之间属于一对多的关联；
        角色可以被赋予任何用户，用户和角色之间是多对多的关联；
        角色拥有可访问的菜单，角色和菜单之间是多对多的关联；
        
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
             * 角色标识
             */
            @TableField(value = "key_")
            private String key;
            /**
             * 角色名称
             */
            @TableField(value = "name_")
            private String name;
            /**
             * 角色状态
             */
            @TableField(value = "status_")
            private String status;
            /**
             * 角色描述
             */
            @TableField(value = "desc_")
            private String desc;
            /**
             * 默认授予登录用户
             */
            @TableField(value = "default_grant_")
            private String defaultGrant;
            /**
             * 优先级（
             多个角色存在冲突时以哪个角色为主，目前主要用于首页地址的设置，
             如应用默认首页是/app但是角色设置为/app1，最终用户登录访问的就是/app1.
             多个角色地址不同时，以优先级高的为主（未设置主页地址的角色不参与评估））
             */
            @TableField(value = "priority_")
            private Integer priority;
            /**
             * 主页地址
             */
            @TableField(value = "main_page_path_")
            private String mainPagePath;
            /**
             * 排序编号
             */
            @TableField(value = "sort_")
            private Integer sort;
        """)
    public List<SysRole> queryRoleListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") SysRoleQueryVo q) {
        q.setEnablePage(Boolean.FALSE);
        return sysRoleService.list(q);
    }
}
