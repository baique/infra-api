package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.util.annotation.Nullable;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysUser.TokenInfoVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;
import tech.hljzj.protect.password.PasswordNotSafeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 sys_user_
 * 业务接口
 *
 * @author wa
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysUser entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysUser entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysUser entity);

    /**
     * 批量删除
     *
     * @param ids 目标实体ID
     * @return 操作是否成功
     */
    boolean entityBatchDelete(Collection<Serializable> ids);

    /**
     * 实体查询
     *
     * @return 操作是否成功
     */
    VSysUser entityGet(VSysUser entity, boolean fetchExtAttr);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    VSysUser entityGet(Serializable id, boolean fetchExtAttr);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<VSysUser> page(VSysUserQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<VSysUser> list(VSysUserQueryVo query);

    /**
     * 判定是否存在
     *
     * @param condition 条件
     * @return 是否存在
     */
    boolean exists(Wrapper<SysUser> condition);


    /**
     * 授予用户可访问的角色
     *
     * @param roleId  角色标识
     * @param userIds 用户标识
     */
    boolean grantRole(String roleId, List<String> userIds);

    /**
     * 取消授权用户可访问的角色
     *
     * @param roleId  角色标识
     * @param userIds 用户标识
     */
    boolean unGrantRole(String roleId, List<String> userIds);

    /**
     * 验证某个角色是否分配给了指定的用户
     *
     * @param roleId  角色标识
     * @param userIds 用户列表
     * @return 分配结果
     */
    Map<String, Boolean> roleAssignedToUsers(String roleId, List<String> userIds);


    /**
     * 获取分配给某个用户的所有角色
     *
     * @param userId 用户标识
     * @return 应用角色信息
     */
    List<GrantAppRoleVo> grantAppWithRole(String userId);


    /**
     * 获取某个应用中分配给某个用户的所有角色
     *
     * @param userId 用户标识
     * @param appId  应用标识
     */
    List<SysRole> listGrantRoleOfUser(String userId, @Nullable String appId);

    /**
     * 查看某用户当前所有在线凭据
     *
     * @param userId 用户标识
     * @return 凭据列表
     */
    List<TokenInfoVo> listOnlineTokens(String userId);

    /**
     * 强制登出某个会话
     *
     * @param tokenValue 会话标识
     */
    void mandatoryLogoutToken(String tokenValue);

    /**
     * 强制登出用户所有会话
     *
     * @param userId 用户标识
     */
    void mandatoryLogout(String userId);

    /**
     * 重置密码
     *
     * @param userId 用户标识
     */
    void resetPassword(String userId);

    /**
     * 修改用户密码
     *
     * @param userId      用户标识
     * @param newPassword 新密码 ，如果新密码传入空，则由系统自动读取默认密码
     */
    void changePassword(String userId, String oldPassword, String newPassword) throws PasswordNotSafeException;

    /**
     * 修改密码
     *
     * @param username    用户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePasswordByUsername(String username, String oldPassword, String newPassword) throws PasswordNotSafeException;

    /**
     * 校验密码强度
     *
     * @param password 密码
     * @param userInfo    用户信息
     */
    void validatePasswordStorage(String password, SysUser userInfo) throws PasswordNotSafeException;

    /**
     * 更新数据排序
     *
     * @param id       数据id
     * @param toPrevId 前一个元素
     * @param toNextId 后一个元素
     */
    void updateSortData(String id, String toPrevId, String toNextId);
}