package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.vo.SysApp.SysAppQueryVo;

import java.util.List;

@Service
public class AppMcpService implements Mcp {
    private final SysAppService sysAppService;

    public AppMcpService(SysAppService sysAppService) {
        this.sysAppService = sysAppService;
    }

    @Tool(name = "queryAppListCondition", description = """
        通过某些条件查询应用，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
            /**
             * 应用标识
             */
            @TableField(value = "key_")
            private String key;
            /**
             * 应用名称
             */
            @TableField(value = "name_")
            private String name;
            /**
             * 主页地址
             */
            @TableField(value = "main_page_path_")
            private String mainPagePath;
            /**
             * 应用密钥
             */
            @TableField(value = "secret_")
            private String secret;
            /**
             * 应用状态
             */
            @TableField(value = "status_")
            private String status;
            /**
             * 应用描述
             */
            @TableField(value = "desc_")
            private String desc;
            /**
             * 排序编号
             */
            @TableField(value = "sort_")
            private Integer sort;
        """)
    public List<SysApp> queryAppListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") SysAppQueryVo q) {
        q.setEnablePage(Boolean.FALSE);
        return sysAppService.list(q);
    }
}
