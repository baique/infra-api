package tech.hljzj.compatible.tyrz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.compatible.tyrz.util.AppHelper;
import tech.hljzj.compatible.tyrz.vo.bean.PageUtil;
import tech.hljzj.compatible.tyrz.vo.permission.Permission;
import tech.hljzj.compatible.tyrz.vo.role.Role;
import tech.hljzj.compatible.tyrz.vo.role.RolePermission;
import tech.hljzj.compatible.tyrz.vo.user.userMini.UserMiniInfo;
import tech.hljzj.compatible.tyrz.vo.user.userWithRolePermission.UserRolePermission;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/app")
@RestController
@Anonymous
public class AppUserAction extends MController {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;
    private final SysMenuService sysMenuService;
    private final SysUserManagerDeptService sysUserManagerDeptService;
    private final SysDeptExternalUserService sysDeptExternalUserService;

    public AppUserAction(SysUserService sysUserService, SysRoleService sysRoleService, SysDeptService sysDeptService, SysMenuService sysMenuService, SysUserManagerDeptService sysUserManagerDeptService, SysDeptExternalUserService sysDeptExternalUserService) {
        super();
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysDeptService = sysDeptService;
        this.sysMenuService = sysMenuService;
        this.sysUserManagerDeptService = sysUserManagerDeptService;
        this.sysDeptExternalUserService = sysDeptExternalUserService;
    }

    /**
     * 用户信息以及用户角色权限信息
     *
     * @return
     */

    @RequestMapping(value = "/userroleandpermission", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object userDetailsInfo(String userId) {
        UserRolePermission userInfo;
        if (StrUtil.isNotBlank(userId)) {
            userInfo = UserRolePermission.from(sysUserService.entityGet(userId, false));
        } else {
            LoginUser user = AppHelper.getLoginUser(request);
            if (user == null) {
                throw UserException.defaultError("请传入用户ID或会话标识");
            }
            userInfo = UserRolePermission.from(sysUserService.entityGet(user.getUserInfo().getId(), false));
        }
        // 获取用户在某接通二中的权限
        String appId = AppHelper.getLoginApp(request);
        // 获取授权给某个用户的角色和权限列表
        List<RolePermission> roles = sysUserService.listGrantRoleOfUser(userInfo.getUserId(), appId).stream()
                .map(f -> {
                    Role r = Role.from(f);
                    return BeanUtil.copyProperties(r, RolePermission.class);
                }).collect(Collectors.toList());
        roles.forEach(item -> item.setPermissions(sysRoleService.listGrantOfRole(item.getRoleId(), appId).stream()
                .map(Permission::from).collect(Collectors.toList())));
        userInfo.setRoles(roles);
        return success(userInfo);
    }

    /**
     * 获取用户列表
     *
     * @param userInfo 动态条件
     * @param page     分页参数
     * @return 允许用户查看的字段组成的bean集合
     */
    @PostMapping("/user/page")
    @ResponseBody
    public Object pageAll(UserMiniInfo userInfo, PageUtil page, Integer hasme, String exField, String exValue) {
        LoginUser currentUser = AppHelper.getLoginUser(request);
        if (currentUser != null) {
            // 用户信息
            userInfo.setUserId(currentUser.getUserInfo().getId());
        }

        VSysUserQueryVo query = userInfo.toQuery();
        query.setPageNum(page.getPage());
        query.setPageSize(page.getLimit());
        //是否包含自身
        boolean hasMe = hasme != null && hasme == 1;
        if (!hasMe) {
            query.setIdNot(userInfo.getUserId());
        }

        //如果部门ID不为空，则查询当前部门及其子部门下的全部用户
        if (StrUtil.isNotBlank(userInfo.getUserDepartment())) {
            query.setBelongDeptId(userInfo.getUserDepartment());
        }
        List<VSysUser> userList;
        long size = 0;
        if (page.isPage()) {
            Page<VSysUser> userPage = sysUserService.page(query);
            userList = userPage.getRecords();
            size = userPage.getTotal();
        } else {
            userList = sysUserService.list(query);
            size = userList.size();
        }
        return success(userList.stream().map(UserMiniInfo::from).collect(Collectors.toList())).addProperty("count", size);
    }

    @PostMapping("/user/all")
    @ResponseBody
    public Object findAll(UserMiniInfo userInfo, Integer hasme) {
        VSysUserQueryVo query = userInfo.toQuery();
        //是否包含自身
        boolean hasMe = hasme != null && hasme == 1;
        if (!hasMe) {
            LoginUser currentUser = AppHelper.getLoginUser(request);
            if (currentUser != null) {
                // 用户信息
                query.setIdNot(currentUser.getUserInfo().getId());
            }
        } else {
            // 如果用户传入了，那么
            query.setId(userInfo.getUserId());
        }

        //如果部门ID不为空，则查询当前部门及其子部门下的全部用户
        if (StrUtil.isNotBlank(userInfo.getUserDepartment())) {
            // 设置其所属的部门标识
            query.setBelongDeptId(userInfo.getUserDepartment());
        }
        List<VSysUser> userList = sysUserService.list(query);
        long size = userList.size();

        return success(userList.stream().map(UserMiniInfo::from).collect(Collectors.toList())).addProperty("count", size);
    }


    @PostMapping("/user/logout")
    @ResponseBody
    public Object logout() {
        try {
            AppHelper.Info loginUser = AppHelper.getLoginInfo(request);
            sysUserService.mandatoryLogoutToken(loginUser.getToken());
        } catch (Exception ignore) {
        }
        return success();
    }


    @PostMapping("/user/dp/get")
    @ResponseBody
    public Object deptGet(String userId) {
        VSysDeptMemberUserQueryVo q = new VSysDeptMemberUserQueryVo();
        q.setId(userId);
        // 用户所属
        q.setOwnerType(2);
        q.setEnablePage(false);
        Page<VSysDeptMemberUser> p = sysDeptService.pageMember(q);
        return R.ok(p.getRecords().stream().map(VSysDeptMemberUser::getDeptId).collect(Collectors.toList()));
    }

    @PostMapping("/user/changePassword")
    @ResponseBody
    public Object changePassword(String password, String newPassword) {
//        try {
//            UserInfo user = UserUtil.getUser(request, true);
//            if (!user.getUserPassword().equals(PasswordUtil.Encryption(password))) {
//                return ApiMsgUtil.failure(BaseCode.ROLLBACK, UserErrorCode.PASSWORDNOTOK);
//            }
//            user.setUserPassword(PasswordUtil.Encryption(newPassword));
//            service(UserInfoService.class).save(user);
//            return ApiMsgUtil.success();
//        } catch (Exception e) {
//            return ApiMsgUtil.failure(BaseCode.USERAUTHENTICATION);
//        }
        //修改密码
        LoginUser user = AppHelper.getLoginUser(request);
        sysUserService.changePassword(user.getUserInfo().getId(), password, newPassword);
        return success();
    }


    @PostMapping("/user/getManagerDept")
    @ResponseBody
    public Object getManagerDept(String userId) {
        //TODO 实现
        return success(null);
    }
}
