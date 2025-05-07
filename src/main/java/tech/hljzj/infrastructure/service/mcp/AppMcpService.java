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

    @Tool(name = "app_all_query", description = "通过条件查询应用")
    public List<SysApp> queryAll(@ToolParam(description = "条件") SysAppQueryVo q) {
        q.setEnablePage(false);
        return this.sysAppService.list(q);
    }

    @Tool(name = "app_all_query_all", description = "查询所有应用")
    public List<SysApp> queryAll() {
        SysAppQueryVo q = new SysAppQueryVo();
        q.setEnablePage(false);
        return this.sysAppService.list(q);
    }

    @Tool(name = "app_all_query_by_name", description = "根据应用名称获取应用")
    public List<SysApp> queryByName(@ToolParam(description = "应用名称") String name) {
        SysAppQueryVo q = new SysAppQueryVo();
        q.setName(name);
        q.setEnablePage(false);
        return this.sysAppService.list(q);
    }

    @Tool(name = "app_all_query_by_id", description = "根据应用标识查询")
    public List<SysApp> queryById(@ToolParam(description = "应用标识") String id) {
        SysAppQueryVo q = new SysAppQueryVo();
        q.setId(id);
        q.setEnablePage(false);
        return this.sysAppService.list(q);
    }
}
