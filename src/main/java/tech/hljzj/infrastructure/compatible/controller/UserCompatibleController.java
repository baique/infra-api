package tech.hljzj.infrastructure.compatible.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.security.BasicAuthenticationManager;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.util.password.ParamEncryption;
import tech.hljzj.infrastructure.compatible.controller.bsae.MController;
import tech.hljzj.infrastructure.compatible.controller.bsae.R;
import tech.hljzj.infrastructure.compatible.util.AppHelper;
import tech.hljzj.infrastructure.compatible.vo.user.userDetails.UserDetails;
import tech.hljzj.infrastructure.config.CompatibleSecurityProvider;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.protect.password.PasswordNotSafeException;

/**
 * 这里是统一认证的兼容接口
 */
@RestController
@Slf4j
@Anonymous
@RequiredArgsConstructor
public class UserCompatibleController extends MController {
    private final BasicAuthenticationManager basicAuthenticationManager;
    private final ParamEncryption encryption;
    private final SysUserService sysUserService;
    private final CompatibleSecurityProvider compatibleSecurityProvider;

    @RequestMapping("server/time")
    public String serverTime() {
        return DateUtil.now();
    }


    /**
     * 这里需要调用登录
     *
     * @param account  账号
     * @param password 密码
     */
    @RequestMapping(value = "/app/login", method = {RequestMethod.GET, RequestMethod.POST})
    @Log(title = "登录", operType = "应用登录", ignoreParamValue = "password")
    @Anonymous
    public R<?> appLogin(String account, String password) {
        try {
            // 需要处理一下密码
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(account, encryption.encrypt(password));
            // 因为这里是使用统一认证登录
            AppScopeHolder.setScopeAppId(AppHelper.getLoginApp(request));

            Authentication authenticate = basicAuthenticationManager.authenticate(auth, compatibleSecurityProvider);
            // 3. 设置认证信息到上下文
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            LoginUser details = (LoginUser) authenticate.getPrincipal();
            //这里直接响应token
            return success(details.getAccessToken());
        } catch (IllegalArgumentException | AuthenticationServiceException e) {
            if (e.getCause() instanceof PasswordNotSafeException) {
                log.warn("客户端请求登录认证失败，因为密码存在问题", e);
                return BeanUtil.copyProperties(R.code(402).setMsg(e.getMessage()), R.class);
            }
            log.warn("客户端请求登录认证失败", e);
            return BeanUtil.copyProperties(R.code(902).setMsg(e.getMessage()), R.class);
        } catch (Exception e) {
            log.warn("客户端请求登录认证失败", e);
            return BeanUtil.copyProperties(R.code(902).setMsg("登录认证失败"), R.class);
        }
    }

    /**
     * 这里需要调用登录
     */
    @RequestMapping(value = "/app/ticket", method = {RequestMethod.GET, RequestMethod.POST})
    @Anonymous
    public R<?> appTicket() {
        try {
            String sign = AppHelper.getSign(request);
            return success(sign);
//            LoginUser loginUser = AppHelper.getLoginUser(request);
//            if (loginUser == null) {
//                return BeanUtil.copyProperties(R.code(902).setMsg("登录会话失效"), R.class);
//            }
//
//
//            String ticket = AppHelper.createTicket(loginUser);

//            return success(ticket);
        } catch (Exception e) {
            log.warn("客户端请求登录认证失败", e);
            return BeanUtil.copyProperties(R.code(902).setMsg("登录认证失败"), R.class);
        }
    }


    @RequestMapping(value = "/app/userlogincheck", method = {RequestMethod.GET, RequestMethod.POST})
    @Anonymous
    public R<?> loginValidate() {
        try {
            LoginUser loginUser = AppHelper.getLoginUser(request);
            if (loginUser == null) {
                return BeanUtil.copyProperties(R.code(902).setMsg("登录会话失效"), R.class);
            }
            return BeanUtil.copyProperties(R.ok(), R.class);
        } catch (Exception e) {
            return BeanUtil.copyProperties(R.code(902).setMsg("登录会话失效"), R.class);
        }
    }

    @RequestMapping(value = "/app/userinfo", method = {RequestMethod.GET, RequestMethod.POST})
    @Anonymous
    public Object userInfo(String userId) {
        VSysUser user;
        if (StrUtil.isNotBlank(userId)) {
            user = sysUserService.entityGet(userId, true);

        } else {
            LoginUser loginInfo = AppHelper.getLoginUser(request);
            if (loginInfo == null) {
                return BeanUtil.copyProperties(fail().setMsg("用户不存在"), R.class);
            }
            user = sysUserService.entityGet(loginInfo.getUserInfo().getId(), true);
        }
        return success(UserDetails.from(user));
    }
}
