package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import lombok.RequiredArgsConstructor;
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
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.password.ParamEncryption;
import tech.hljzj.framework.util.password.SMUtil;
import tech.hljzj.framework.util.web.AuthUtil;
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
import tech.hljzj.infrastructure.util.UserPasswordContext;
import tech.hljzj.infrastructure.vo.SysApp.SysAppListVo;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysRole.SysGrantRoleListVo;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.SysUser.TokenInfoVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;
import tech.hljzj.protect.password.PasswordNotSafeException;
import tech.hljzj.protect.password.PasswordScorer;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户管理 sys_user_
 * 业务实现
 *
 * @author wa
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleMapper userRoleMapper;
    private final SysUserViewMapper userViewMapper;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
    private final SysAppService sysAppService;
    private final SessionStoreDecorator sessionStoreDecorator;
    private final SysConfigService sysConfigService;
    private final SwapEncoder swapEncoder;
    private final SortService sortService;
    private final ParamEncryption paramEncryption;
    @Autowired
    @Lazy
    private LocalSecurityProvider localSecurity;
    @Autowired
    private SysUserExtAttrService sysUserExtAttrService;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean entityCreate(SysUser entity, Map<String, Object> attrs) {
        if (entityCreate(entity)) {
            updateAttribution(entity.getId(), attrs);
            return true;
        }
        return false;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean entityUpdate(SysUser entity, Map<String, Object> attrs) {
        entityUpdate(entity);
        updateAttribution(entity.getId(), attrs);
        return false;
    }


    private void updateAttribution(String id, Map<String, Object> attribution) {
        if (attribution != null) {
            SysUserExtAttr attr = new SysUserExtAttr();
            attr.setId(id);
            attr.setAttribution(/*JSONUtil.toJsonStr*/(attribution));
            // save or update
            sysUserExtAttrService.saveOrUpdate(attr);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityDelete(SysUser entity) {
        if (AuthUtil.isLogin()) {
            UserInfo userInfo = AuthUtil.getLoginUserDetail();
            if (userInfo.getId().equals(entity.getId())) {
                // 此时要进行验证
                String valueByKey = sysConfigService.getValueByKey(AppConst.CONFIG_ALLOW_SELF_DROP);
                if (!AppConst.YES.equals(valueByKey)) {
                    throw UserException.defaultError("系统禁止删除您正在使用的账号");
                }
            }
        }
        // 与用户关联的角色需要删除
        userRoleMapper.delete(Wrappers.lambdaQuery(SysUserRole.class)
            .eq(SysUserRole::getUserId, entity.getId())
        );

        // 用户在部门内应该是有
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        for (SysUser item : listByIds(ids)) {
            entityDelete(item);
        }
        return true;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean grantRoles(String userId, List<String> roleIds) {
        sysUserRoleService.saveBatch(sysRoleService.listByIds(roleIds).stream().map(f -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setAppId(f.getOwnerAppId());
            userRole.setRoleId(f.getId());
            return userRole;
        }).collect(Collectors.toSet()));
        return true;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean unGrantRoles(String userId, List<String> roleIds) {
        sysUserRoleService.remove(Wrappers
            .<SysUserRole>lambdaQuery()
            .in(SysUserRole::getRoleId, roleIds)
            .eq(SysUserRole::getUserId, userId)
        );
        return true;
    }

    /**
     * 修改角色的过期时间
     * @param userId 用户标识
     * @param roleId 角色标识
     * @param expiredTime 过期时间
     * @return 是否成功修改
     */
    public boolean updateGrantRoleExpiredTime(String userId, String roleId, Date expiredTime) {
        if (expiredTime != null) {
            return sysUserRoleService.update(
                Wrappers.<SysUserRole>lambdaUpdate()
                    .eq(SysUserRole::getUserId, userId)
                    .eq(SysUserRole::getRoleId, roleId)
                    .set(SysUserRole::getExpiredTime, expiredTime)
            );
        } else {
            return sysUserRoleService.update(
                Wrappers.<SysUserRole>lambdaUpdate()
                    .eq(SysUserRole::getUserId, userId)
                    .eq(SysUserRole::getRoleId, roleId)
                    .setSql("expired_time_ = null")
            );
        }
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
    public List<GrantAppRoleVo> grantAppWithRole(String userId, String appId) {
        // 这将同时得到应用和角色标识
        List<SysUserRole> grantUserRoleIds = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
            .eq(SysUserRole::getUserId, userId)
            .eq(StrUtil.isNotBlank(appId), SysUserRole::getAppId, appId)
        );
        Map<String, List<SysUserRole>> appRoleGroup = grantUserRoleIds
            .stream().collect(Collectors.groupingBy(SysUserRole::getAppId));

        if (CollUtil.isEmpty(appRoleGroup)) {
            return Collections.emptyList();
        }

        return sysAppService.listByIds(appRoleGroup.keySet()).stream().map(app -> {
                Map<String, SysUserRole> userRoleMap = new HashMap<>();
                Set<String> roleIds = appRoleGroup.get(app.getId())
                    .stream()
                    .peek(f -> userRoleMap.put(f.getRoleId(), f))
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toSet());
                List<SysGrantRoleListVo> roleList = sysRoleService
                    .listByIds(roleIds).stream()
                    .map(f -> new SysGrantRoleListVo().<SysGrantRoleListVo>fromDto(f))
                    .peek(f -> {
                        SysUserRole sysUserRole = userRoleMap.get(f.getId());
                        f.setExpiredTime(sysUserRole.getExpiredTime());
                    })
                    .collect(Collectors.toList());
                GrantAppRoleVo grantAppRoleVo = new GrantAppRoleVo();
                grantAppRoleVo.setRoleList(roleList);
                grantAppRoleVo.setAppInfo(new SysAppListVo().fromDto(app));
                return grantAppRoleVo;
            }).sorted((app1, app2) -> CompareUtil.compare(app1.getAppInfo().getSort(), app2.getAppInfo().getSort()))
            .collect(Collectors.toList());
    }

    @Override
    public List<SysLoginBindRole> listGrantRoleOfUser(String userId, @Nullable String appId) {
        Date loginDate = new Date();
        List<SysUserRole> sysUserRoles = userRoleMapper.selectList(Wrappers
            .<SysUserRole>lambdaQuery()
            .eq(SysUserRole::getUserId, userId)
            .eq(SysUserRole::getAppId, appId)
        ).stream().filter(f -> {
            if (ObjUtil.isNull(f.getExpiredTime())) {
                return true;
            }
            return f.getExpiredTime().after(loginDate);
        }).toList();

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
        return sysRoleService.list(queryWrapper).stream()
            .map(f -> {
                SysLoginBindRole sysLoginBindRole = new SysLoginBindRole().fromDto(f);
                sysLoginBindRole.setSource("用户自有角色");
                return sysLoginBindRole;
            }).collect(Collectors.toList());
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
            // 找出用户记录用户的密码
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
    public void changePasswordByUsername(String username, String oldPassword, String newPassword) throws PasswordNotSafeException {
        oldPassword = getRawPassword(oldPassword);
        newPassword = getRawPassword(newPassword);

        SysUser user = getOne(Wrappers.<SysUser>lambdaQuery()
            .eq(SysUser::getUsername, username)
        );
        if (user == null) {
            throw UserException.defaultError("用户名或密码错误导致失败");
        }
        // 临时设置为不检查
        user.setPasswordPolicy(AppConst.PASSWORD_POLICY.NEVER);
        validatePasswordStorage(newPassword, user);
        // 检查密码
        if (!localSecurity.validatePassword(oldPassword, user)) {
            throw UserException.defaultError("用户名或密码错误导致失败");
        }
        try {
            update(Wrappers.lambdaUpdate(SysUser.class)
                .eq(SysUser::getId, user.getId())
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
    public void changePassword(String userId, String oldPassword, String newPassword) throws PasswordNotSafeException {
        oldPassword = getRawPassword(oldPassword);
        newPassword = getRawPassword(newPassword);
        SysUser user = getById(userId);
        validatePasswordStorage(newPassword, user);
        //这里需要使用登录认证的类似逻辑
        if (localSecurity.validatePassword(oldPassword, user)) {
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


    public String getRawPassword(String encPass) {
        return paramEncryption.decrypt(encPass);
    }

    @Override
    public void validatePasswordStorage(String password, SysUser user) throws PasswordNotSafeException {
        this.validatePasswordStorage(password, UserPasswordContext.getContext(user));
    }

    protected void validatePasswordStorage(String newPassword, List<String> userMoreInfo) throws PasswordNotSafeException {
        // 密码强度检查
        String passwordStrengthPattern = sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_STRENGTH);
        // 密码最低强度分，基于zxcvbn
        int passwordScore = Integer.parseInt(sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_SCORE));
        // 低于指定强度时提示信息
        String errorMessage = sysConfigService.getValueByKey(AppConst.CONFIG_PASSWORD_STRENGTH_DESC);
        PasswordScorer.validatePassword(newPassword,
            passwordStrengthPattern,
            passwordScore,
            errorMessage,
            userMoreInfo
        );
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