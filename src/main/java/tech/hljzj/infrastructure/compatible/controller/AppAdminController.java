package tech.hljzj.infrastructure.compatible.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.hljzj.infrastructure.compatible.util.AppHelper;
import tech.hljzj.infrastructure.compatible.vo.department.Department;
import tech.hljzj.infrastructure.compatible.vo.role.Role;
import tech.hljzj.framework.security.SessionStoreDecorator;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Anonymous
@Controller
public class AppAdminController extends MController {
    private final SysDeptService sysDeptService;
    private final SysUserService sysUserService;
    private final SessionStoreDecorator sessionStoreDecorator;
    private final SysRoleService sysRoleService;

    public AppAdminController(SysDeptService sysDeptService, SysUserService sysUserService, SessionStoreDecorator sessionStoreDecorator, SysRoleService sysRoleService) {
        super();
        this.sysDeptService = sysDeptService;
        this.sysUserService = sysUserService;
        this.sessionStoreDecorator = sessionStoreDecorator;
        this.sysRoleService = sysRoleService;
    }

    @PostMapping("/tyrz/departments/dp/get")
    @ResponseBody
    public Object users(String departmentId) {
        VSysDeptMemberUserQueryVo q = new VSysDeptMemberUserQueryVo();
        q.setDeptId(departmentId);
        q.setEnablePage(false);

        Page<VSysDeptMemberUser> page = sysDeptService.pageMember(q);
        return R.ok(page.getRecords().stream().map(SysUser::getId).collect(Collectors.toList()));
    }

    @RequestMapping("/tyrz/apptokens")
    @ResponseBody
    public Object tokens(String token) {
        Map<String, String> tk = new LinkedHashMap<>();
        tk.put("id", token);
        return R.ok(Collections.singletonList(
                tk
        ));
    }

    @RequestMapping("/tyrz/getroles")
    @ResponseBody
    public Object getRoles(Integer page, Integer limit, String appId) {
        SysRoleQueryVo rq = new SysRoleQueryVo();
        rq.setPageNum(page);
        rq.setPageSize(limit);
        rq.setOwnerAppId(appId);
        List<SysRole> roleList = sysRoleService.list(rq);
        return success(roleList.stream().map(Role::from).collect(Collectors.toList()));
    }


    @RequestMapping("/tyrz/departments")
    public Object departments(Integer page, Integer limit) {
        SysDeptQueryVo rq = new SysDeptQueryVo();
        rq.setPageNum(page);
        rq.setPageSize(limit);
        List<SysDept> roleList = sysDeptService.list(rq);
        return success(roleList.stream().map(Department::from).collect(Collectors.toList()));
    }


    @RequestMapping("/tyrz/deleteapptoken")
    public Object deleteToken(String id) {
        sessionStoreDecorator.removeToken(id);
        return R.ok();
    }

    @PostMapping("/resource")
    public String resource(String redirect) {
        return "forward:/tyrz/" + redirect;
    }

    @RequestMapping("/tyrz/getToken")
    @ResponseBody
    public Object getToken(String ticket) {
        try {
            return R.ok(AppHelper.getLoginInfo(ticket));
        } catch (Exception e) {
            return R.fail().setMsg("用户登录状态可能已失效！");
        }
    }
}
