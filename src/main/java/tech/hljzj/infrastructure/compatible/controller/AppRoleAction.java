package tech.hljzj.infrastructure.compatible.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.infrastructure.compatible.controller.bsae.MController;
import tech.hljzj.infrastructure.compatible.util.AppHelper;
import tech.hljzj.infrastructure.compatible.vo.bean.PageUtil;
import tech.hljzj.infrastructure.compatible.vo.role.Role;
import tech.hljzj.infrastructure.compatible.vo.user.userDetails.UserDetails;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserLoginService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/app")
@RestController
@Anonymous
@RequiredArgsConstructor
public class AppRoleAction extends MController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysUserLoginService sysUserLoginService;

    @PostMapping("/role/hasRoleUsers")
    @ResponseBody
    public Object findHasPermissionUsers(Role roleInfo) {
        List<String> roleList = sysRoleService.list(roleInfo.toQuery()).stream().map(SysRole::getId).collect(Collectors.toList());
        VSysUserQueryVo q = new VSysUserQueryVo();
        q.setHasRole(roleList);
        return success(sysUserService.list(q)
            .stream().map(UserDetails::from).collect(Collectors.toList())
        );
    }


    @PostMapping("/role/page")
    @ResponseBody
    public Object pageAll(Role roleInfo, PageUtil page) {
        SysRoleQueryVo query = roleInfo.toQuery();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        long size;
        List<Role> roles;
        if (page.isPage()) {
            query.setPageNum(page.getPage());
            query.setPageSize(page.getLimit());
            Page<SysRole> rolePage = sysRoleService.page(query);
            roles = rolePage.getRecords().stream().map(Role::from).collect(Collectors.toList());
            size = rolePage.getTotal();
        } else {
            roles = sysRoleService.list(query).stream().map(Role::from).collect(Collectors.toList());
            size = roles.size();
        }
        return success(roles).addProperty("count", size);
    }


    @PostMapping("/role/all")
    @ResponseBody
    public Object findAll(Role roleInfo) {
        SysRoleQueryVo query = roleInfo.toQuery();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        List<Role> rolepage = sysRoleService.list(query).stream().map(Role::from).collect(Collectors.toList());
        return success(rolepage).addProperty("count", rolepage.size());
    }

    /*
     * 根据用户ID 应用ID 获取关联角色
     *
     */

    @PostMapping("/roleByUser")
    @ResponseBody
    private Object findUserRoles(String userId) {
        SysUser u = sysUserService.getById(userId);
        List<SysLoginBindRole> roles;
        if (u == null) {
            roles = Collections.emptyList();
        } else {
            //获取某个用户持有的角色
            roles = sysUserLoginService.listGrantRoleOfUserAndDept(userId, AppHelper.getLoginApp(request), u.getDeptId());

        }

        List<Role> roleList = roles.stream()
            .map(Role::from)
            .collect(Collectors.toList());
        return success(roleList).addProperty("count", roleList.size());
    }
}
