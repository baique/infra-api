package tech.hljzj.infrastructure.service.mcp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysUserRole;
import tech.hljzj.infrastructure.service.SysUserRoleService;

import java.util.List;

@Service
public class UserRoleMcpService implements Mcp {
    private final SysUserRoleService sysUserRoleService;

    public UserRoleMcpService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Tool(name = "queryUserRoleListCondition",
        description = """
            通过某些条件查询用户可用的角色，返回信息中包含用户标识、角色标识和应用标识
            """)
    public List<SysUserRole> queryUserRoleListCondition(
        @ToolParam(description = "用户标识", required = false) String userId,
        @ToolParam(description = "角色标识", required = false) String roleId,
        @ToolParam(description = "应用标识", required = false) String appId
    ) {
        SysUserRole rq = new SysUserRole();
        rq.setUserId(userId);
        rq.setRoleId(roleId);
        rq.setAppId(appId);
        return sysUserRoleService.list(Wrappers.query(rq));
    }
}
