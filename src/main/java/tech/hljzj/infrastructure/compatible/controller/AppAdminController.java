package tech.hljzj.infrastructure.compatible.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.hljzj.framework.security.SessionStoreDecorator;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.password.ParamEncryption;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.compatible.controller.bsae.MController;
import tech.hljzj.infrastructure.compatible.controller.bsae.R;
import tech.hljzj.infrastructure.compatible.util.AppHelper;
import tech.hljzj.infrastructure.compatible.vo.AppInfo;
import tech.hljzj.infrastructure.compatible.vo.department.Department;
import tech.hljzj.infrastructure.compatible.vo.role.Role;
import tech.hljzj.infrastructure.config.AppLoginUserInfo;
import tech.hljzj.infrastructure.config.LocalSecurityProvider;
import tech.hljzj.infrastructure.config.TokenAuthenticateService;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysApp.SysAppQueryVo;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;
import tech.hljzj.protect.password.PasswordNotSafeException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Anonymous
@Controller
@Slf4j
public class AppAdminController extends MController {
    private final SysDeptService sysDeptService;
    private final SysUserService sysUserService;
    private final SessionStoreDecorator sessionStoreDecorator;
    private final SysRoleService sysRoleService;
    private final SysAppService sysAppService;
    private final TokenAuthenticateService tokenAuthenticateService;
    private final ParamEncryption paramEncryption;

    public AppAdminController(SysDeptService sysDeptService, SysUserService sysUserService, SessionStoreDecorator sessionStoreDecorator, SysRoleService sysRoleService, SysAppService sysAppService, LocalSecurityProvider localSecurityProvider, TokenAuthenticateService tokenAuthenticateService, ParamEncryption paramEncryption) {
        super();
        this.sysDeptService = sysDeptService;
        this.sysUserService = sysUserService;
        this.sessionStoreDecorator = sessionStoreDecorator;
        this.sysRoleService = sysRoleService;
        this.sysAppService = sysAppService;
        this.tokenAuthenticateService = tokenAuthenticateService;
        this.paramEncryption = paramEncryption;
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

    @RequestMapping("/admin/updatePassWordInLogin")
    @ResponseBody
    @Anonymous
    @Deprecated
    public Object updatePassword(String userAccount, String userPassword, String initialPassWord) throws PasswordNotSafeException {
        SysUser u = null;
        try {
            u = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                    .eq(SysUser::getUsername, userAccount),
                true
            );
        } catch (Exception e) {
            log.error("用户信息查找失败", e);
        }
        if (u == null) {
            return R.fail().setMsg("未找到用户");
        }
        // 这里因为是兼容接口不对原密码是否加密进行检查；所以此处进行了主动加密
        // 原密码
        userPassword = paramEncryption.encrypt(userPassword);
        initialPassWord = paramEncryption.encrypt(initialPassWord);

        sysUserService.changePassword(u.getId(), initialPassWord, userPassword);
        return R.ok();
    }

    @RequestMapping("/tyrz/getroles")
    @ResponseBody
    public Object getRoles(Role roleInfo, Integer page, Integer limit, String appId) {
        SysRoleQueryVo rq = roleInfo.toQuery();
        rq.setPageNum(page);
        rq.setPageSize(limit);
        rq.setOwnerAppId(appId);
        List<SysRole> roleList = sysRoleService.list(rq);
        return success(roleList.stream().map(Role::from).collect(Collectors.toList()));
    }


    @RequestMapping("/tyrz/departments")
    @ResponseBody
    public Object departments(Department department, Integer page, Integer limit) {
        SysDeptQueryVo rq = department.toQuery();
        rq.setPageNum(page);
        rq.setPageSize(limit);
        List<SysDept> roleList = sysDeptService.list(rq);
        return success(roleList.stream().map(Department::from).collect(Collectors.toList()));
    }

    @RequestMapping("/tyrz/scopeapps")
    @ResponseBody
    public Object departments(String appName, Integer page, Integer limit) {
        SysAppQueryVo rq = new SysAppQueryVo();
        rq.setNameLike(appName);
        rq.setPageNum(page);
        rq.setPageSize(limit);
        List<SysApp> appList = sysAppService.list(rq);
        return success(appList.stream().map(AppInfo::fromDto).collect(Collectors.toList()));
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
    public Object getToken(String token, String ticket) {
        try {

            TokenAuthentication currentServerAuth;
            AppHelper.Info loginInfo;
            if (StrUtil.isNotBlank(ticket)) {
                loginInfo = AppHelper.getLoginInfo(ticket);
                // 此处需要将用户登录的系统转换为基座服务本身的token，所以需要登录基座服务获取token
                AppScopeHolder.setScopeAppId(AppConst.ID);
                currentServerAuth = tokenAuthenticateService.authenticate(loginInfo.getToken(), true);
            } else {
                currentServerAuth = tokenAuthenticateService.authenticate(token, true);
                loginInfo = new AppHelper.Info();
                loginInfo.setToken(currentServerAuth.getToken());
                UserInfo userInfo = currentServerAuth.getPrincipal().getUserInfo();
                if (userInfo instanceof AppLoginUserInfo u) {
                    loginInfo.setAppId(u.getLoginAppId());
                }
            }


            // 使用ticket拿到了token，然后需要输出token的内容
            SysApp sysApp = sysAppService.entityGet(loginInfo.getAppId());
            // 这里变更了登录指向
            loginInfo.setAppId(AppConst.ID);
            loginInfo.setAppInfo(sysApp);
            loginInfo.setToken(currentServerAuth.getToken());

            return R.ok(loginInfo);
        } catch (Exception e) {
            return R.fail().setMsg("用户登录状态可能已失效！");
        }
    }


}
