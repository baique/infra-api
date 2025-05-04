package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.util.annotation.Nullable;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.SessionStoreDecorator;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.password.SMUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.config.AppLoginUserInfo;
import tech.hljzj.infrastructure.config.LocalSecurityProvider;
import tech.hljzj.infrastructure.config.SwapEncoder;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.mapper.SysUserMapper;
import tech.hljzj.infrastructure.mapper.SysUserRoleMapper;
import tech.hljzj.infrastructure.mapper.SysUserViewMapper;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.SysApp.SysAppListVo;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleListVo;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.SysUser.TokenInfoVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 用户管理 sys_user_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserViewMapper userViewMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysAppService sysAppService;
    @Autowired
    private SessionStoreDecorator sessionStoreDecorator;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    @Lazy
    private LocalSecurityProvider localSecurityProvider;
    @Autowired
    private SwapEncoder swapEncoder;
    @Autowired
    private SortService sortService;

    @Override
    public VSysUser entityGet(VSysUser entity, boolean fetchExtAttr) {
        return userViewMapper.selectJoinOne(VSysUser.class, MPJWrappers.lambdaJoin(entity)
                .selectAll(VSysUser.class)
                .selectAs(SysUserExtAttr::getAttribution, VSysUser::getAttribution)
                .leftJoin(SysUserExtAttr.class, SysUserExtAttr::getId, VSysUser::getId)
        );
    }

    @Override
    public VSysUser entityGet(Serializable id, boolean fetchExtAttr) {
        return userViewMapper.selectJoinOne(VSysUser.class, MPJWrappers.<VSysUser>lambdaJoin()
                .selectAll(VSysUser.class)
                .selectAs(SysUserExtAttr::getAttribution, VSysUser::getAttribution)
                .leftJoin(SysUserExtAttr.class, SysUserExtAttr::getId, VSysUser::getId)
                .eq(VSysUser::getId, id)
        );
    }


    @Override
    public boolean entityCreate(SysUser entity) {
        if (exists(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, entity.getUsername()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "账号"));
        }

        if (StrUtil.isNotBlank(entity.getCardNo()) && exists(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getCardType, entity.getCardType())
                .eq(SysUser::getCardNo, entity.getCardNo()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "证件号码"));
        }
        if (StrUtil.isNotBlank(entity.getPhone()) && exists(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, entity.getPhone()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "手机号码"));
        }
        // 密码加密存储，使用sm3方式加密
        entity.setPassword(SMUtil.sm3(entity.getPassword()));

        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysUser entity) {
        if (exists(Wrappers.<SysUser>lambdaQuery().ne(SysUser::getId, entity.getId()).eq(SysUser::getUsername, entity.getUsername()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "账号"));
        }

        if (StrUtil.isNotBlank(entity.getCardNo()) && exists(Wrappers.<SysUser>lambdaQuery()
                .ne(SysUser::getId, entity.getId())
                .eq(SysUser::getCardType, entity.getCardType())
                .eq(SysUser::getCardNo, entity.getCardNo()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "证件号码"));
        }
        if (StrUtil.isNotBlank(entity.getPhone()) && exists(Wrappers.<SysUser>lambdaQuery().ne(SysUser::getId, entity.getId()).eq(SysUser::getPhone, entity.getPhone()))) {
            throw UserException.defaultError(MsgUtil.t("user.exists", "手机号码"));
        }

        SysUser existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysUser entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<VSysUser> page(VSysUserQueryVo query) {
        Page<VSysUser> pageConfig = query.buildPagePlus();
        return userViewMapper.selectPage(pageConfig, query.buildQueryWrapper());
    }


    @Override
    public List<VSysUser> list(VSysUserQueryVo query) {
        query.setEnablePage(false);
        return page(query).getRecords();
    }

    @Override
    public boolean grantRole(String roleId, List<String> userIds) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            return false;
        }

        Map<String, List<SysUserRole>> existValue = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, userIds)
        ).stream().collect(Collectors.groupingBy(SysUserRole::getUserId));

        return sysUserRoleService.saveBatch(userIds.stream().filter(userId -> !existValue.containsKey(userId)).map(userId -> {
            //这里执行插入
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setAppId(role.getOwnerAppId());
            userRole.setRoleId(roleId);
            return userRole;

        }).collect(Collectors.toSet()));
    }

    @Override
    public boolean unGrantRole(String roleId, List<String> userIds) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            return false;
        }
        return sysUserRoleService.remove(Wrappers
                .<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, userIds)
        );
    }

    @Override
    public boolean exists(Wrapper<SysUser> eq) {
        return baseMapper.exists(eq);
    }

    @Override
    public Map<String, Boolean> roleAssignedToUsers(String roleId, List<String> userIds) {

        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        List<SysUserRole> sysUserRoles = userRoleMapper.selectList(Wrappers
                .<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, userIds)
        );

        Map<String, Boolean> result = new HashMap<>();
        sysUserRoles.forEach(f -> {
            String userId = f.getUserId();
            userIds.remove(userId);
            result.put(userId, true);
        });
        //剩余的userId就是没有此角色的用户
        userIds.forEach(f -> {
            result.put(f, false);
        });

        return result;
    }

    @Override
    public List<GrantAppRoleVo> grantAppWithRole(String userId) {
        // 这将同时得到应用和角色标识
        List<SysUserRole> grantUserRoleIds = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
        );
        Map<String, List<SysUserRole>> appRoleGroup = grantUserRoleIds
                .stream().collect(Collectors.groupingBy(SysUserRole::getAppId));

        if (CollUtil.isEmpty(appRoleGroup)) {
            return Collections.emptyList();
        }

        return sysAppService.listByIds(appRoleGroup.keySet()).stream().map(app -> {
                    Set<String> roleIds = appRoleGroup.get(app.getId()).stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
                    List<SysRoleListVo> roleList = sysRoleService
                            .listByIds(roleIds).stream()
                            .map(f -> new SysRoleListVo().<SysRoleListVo>fromDto(f))
                            .collect(Collectors.toList());
                    GrantAppRoleVo grantAppRoleVo = new GrantAppRoleVo();
                    grantAppRoleVo.setRoleList(roleList);
                    grantAppRoleVo.setAppInfo(new SysAppListVo().fromDto(app));
                    return grantAppRoleVo;
                }).sorted((app1, app2) -> CompareUtil.compare(app1.getAppInfo().getSort(), app2.getAppInfo().getSort()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysRole> listGrantRoleOfUser(String userId, @Nullable String appId) {
        List<SysUserRole> sysUserRoles = userRoleMapper.selectList(Wrappers
                .<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getAppId, appId)
        );
        SysRoleQueryVo q = new SysRoleQueryVo();
        LambdaQueryWrapper<SysRole> queryWrapper = q.buildQueryWrapper();
        // 获取应用默认授予用户的角色，与自然角色

        if (Objects.nonNull(appId)) {
            queryWrapper.eq(SysRole::getOwnerAppId, appId);
        } else if (CollUtil.isEmpty(sysUserRoles)) {
            return Collections.emptyList();
        }
        queryWrapper.and(or -> {
            or.or(CollUtil.isNotEmpty(sysUserRoles), i -> i.in(SysRole::getId, sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList())));
            or.or(i -> i.eq(SysRole::getDefaultGrant, AppConst.YES));
        });
        queryWrapper.orderByAsc(SysRole::getSort);
        queryWrapper.orderByDesc(SysRole::getId);
        return sysRoleService.list(queryWrapper);
    }

    @Override
    public List<TokenInfoVo> listOnlineTokens(String userId) {
        Collection<String> tokenList = sessionStoreDecorator.getUserTokens(userId);
        List<TokenInfoVo> list = new ArrayList<>();
        MultiValueMap<String, TokenInfoVo> appWithToken = new LinkedMultiValueMap<>();
        for (String token : tokenList) {
            Optional<TokenAuthentication> userInfo = sessionStoreDecorator.getUserByToken(token);
            userInfo.ifPresent(f -> {
                TokenInfoVo tokenInfo = new TokenInfoVo();
                tokenInfo.setTokenValue(f.getToken());
                tokenInfo.setUseAgent(f.getUseAgent());
                tokenInfo.setLoginTime(new Date(f.getLoginTime()));
                tokenInfo.setLastActiveTime(new Date(f.getLastActiveTime()));
                AppLoginUserInfo appUserInfo = (AppLoginUserInfo) f.getPrincipal().getUserInfo();
                if (appUserInfo != null) {
                    appWithToken.add(appUserInfo.getLoginAppId(), tokenInfo);
                }
                list.add(tokenInfo);
            });
        }
        if (CollUtil.isEmpty(appWithToken)) {
            return list;
        }
        sysAppService.listByIds(appWithToken.keySet()).forEach(item -> {
            List<TokenInfoVo> tokens = appWithToken.get(item.getId());
            assert tokens != null;
            tokens.forEach(t -> t.setLoginApp(item));
        });
        return list;
    }

    @Override
    public void mandatoryLogoutToken(String tokenValue) {
        sessionStoreDecorator.removeToken(tokenValue);
    }

    @Override
    public void mandatoryLogout(String userId) {
        Collection<String> tokenList = sessionStoreDecorator.getUserTokens(userId);
        tokenList.forEach(f -> {
            sessionStoreDecorator.removeToken(f);
        });
    }

    @Override
    public void resetPassword(String userId) {
        String defaultPassword = sysConfigService.getValueByKey(AppConst.CONFIG_DEFAULT_PASSWORD);
        if (StrUtil.isBlank(defaultPassword)) {
            throw UserException.defaultError("管理员尚未配置默认密码，请联系管理配置后使用此功能！");
        }
        try {
            update(Wrappers.lambdaUpdate(SysUser.class)
                    .eq(SysUser::getId, userId)
                    .set(SysUser::getPassword, SMUtil.sm3(defaultPassword))
                    .set(SysUser::getMaskV, swapEncoder.encode(defaultPassword))
                    .set(SysUser::getLastChangePassword, new Date())
            );
        } catch (Exception e) {
            throw UserException.defaultError("密码修改失败", e);
        }
    }

    @Override
    public void changePasswordByUsername(String username, String oldPassword, String newPassword) {
        SysUser us = getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username)
        );
        if (us == null) {
            throw UserException.defaultError("用户名或密码错误导致失败");
        }
        // 临时设置为不检查
        us.setPasswordPolicy(AppConst.PASSWORD_POLICY.NEVER);
        // 检查密码
        if (!localSecurityProvider.validatePassword(oldPassword, us)) {
            throw UserException.defaultError("用户名或密码错误导致失败");
        }
        try {
            update(Wrappers.lambdaUpdate(SysUser.class)
                    .eq(SysUser::getId, us.getId())
                    .set(SysUser::getPasswordPolicy, AppConst.PASSWORD_POLICY.NEVER)
                    .set(SysUser::getPassword, SMUtil.sm3(newPassword))
                    .set(SysUser::getMaskV, swapEncoder.encode(newPassword))
                    .set(SysUser::getLastChangePassword, new Date())
            );
        } catch (Exception e) {
            throw UserException.defaultError("密码修改失败", e);
        }
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        SysUser user = getById(userId);

        // 首先检查密码
        String passwordStrength = sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_STRENGTH);
        String desc = sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_STRENGTH_DESC);
        if (StrUtil.isNotEmpty(passwordStrength)) {
            //如果设置了强度规则，那么就用这个规则进行检查
            try {
                Pattern pp = Pattern.compile(passwordStrength);
                Matcher matcher = pp.matcher(newPassword);
                if (!matcher.matches()) {
                    throw UserException.defaultError("密码内容不符合强度要求" + (StrUtil.isBlank(desc) ? "" : ":" + desc));
                }
            } catch (Exception e) {
                throw UserException.defaultError("密码强度规则设置有误，无法正确检测密码强弱", e);
            }
        }

        //这里需要使用登录认证的类似逻辑
        if (localSecurityProvider.validatePassword(oldPassword, user)) {
            try {
                update(Wrappers.lambdaUpdate(SysUser.class)
                        .eq(SysUser::getId, user.getId())
                        .set(SysUser::getPassword, SMUtil.sm3(newPassword))
                        .set(SysUser::getMaskV, swapEncoder.encode(newPassword))
                        .set(SysUser::getLastChangePassword, new Date())
                );
            } catch (Exception e) {
                throw UserException.defaultError("密码修改失败", e);
            }
            return;
        }
        throw UserException.defaultError("原密码错误");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSortData(String id, String toPrevId, String toNextId) {
        updateBatchById(sortService.applySort(
                id, toPrevId, toNextId,
                baseMapper,
                q -> q.where()
                        .setEntityClass(SysUser.class)
                        .orderByAsc(SysUser::getSort)
                        .orderByDesc(SysUser::getCreateTime)
                        .orderByDesc(SysUser::getId)
        ));
    }
}