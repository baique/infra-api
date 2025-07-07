package tech.hljzj.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysUserLoginService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SysUserLoginServiceImpl implements SysUserLoginService {
    private final SysDeptService sysDeptService;
    private final SysUserService sysUserService;

    @Override
    public List<SysLoginBindRole> listGrantRoleOfUserAndDept(String userId, @Nullable String appId) {
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        // 用户所属部门
        return listGrantRoleOfUserAndDept(userId, appId, user.getDeptId());
    }


    @Override
    public List<SysLoginBindRole> listGrantRoleOfUserAndDept(String userId, String appId, String deptId) {
        List<SysLoginBindRole> roleList = sysUserService.listGrantRoleOfUser(userId, appId);
        List<SysLoginBindRole> deptRoleList = sysDeptService.listGrantRoleOfDept(appId, deptId);
        List<SysLoginBindRole> grantedRoleList = new ArrayList<>();
        Set<String> idSet = new HashSet<>();

        for (SysLoginBindRole role : roleList) {
            if (idSet.add(role.getId())) {
                grantedRoleList.add(role);
            }
        }

        for (SysLoginBindRole role : deptRoleList) {
            if (idSet.add(role.getId())) {
                grantedRoleList.add(role);
            }
        }
        grantedRoleList.sort(Comparator.comparing(SysLoginBindRole::getSort));

        return grantedRoleList;
    }

}
