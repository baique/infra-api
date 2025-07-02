package tech.hljzj.infrastructure.service;

import reactor.util.annotation.Nullable;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;

import java.util.List;

public interface SysUserLoginService {

    /**
     * 获取用户授权
     *
     * @param userId 用户id
     * @param appId  应用id
     * @return 角色列表
     */
    List<SysLoginBindRole> listGrantRoleOfUserAndDept(String userId, @Nullable String appId);

    /**
     * 获取用户授权
     *
     * @param userId 用户id
     * @param appId  应用id
     * @param deptId 部门id
     * @return 角色列表
     */
    List<SysLoginBindRole> listGrantRoleOfUserAndDept(String userId, @Nullable String appId, String deptId);
}
