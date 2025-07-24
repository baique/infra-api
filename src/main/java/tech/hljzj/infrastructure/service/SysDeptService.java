package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.util.annotation.Nullable;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptAncestors;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 组织管理 sys_dept
 * 业务接口
 *
 * @author wa
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDept entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDept entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDept entity);

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
    SysDept entityGet(SysDept entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDept entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysDept> page(SysDeptQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDept> list(SysDeptQueryVo query);

    /**
     * 获取某个部门的所有后代
     * 包含自身
     */
    List<SysDept> descendantsAll(String deptId);

    /**
     * 查询某个部门的所有祖先节点
     *
     * @param deptId 部门标识
     * @return 组件节点数据列表
     */
    SysDeptAncestors ancestorNodes(String deptId);


    /**
     * 授予用户可访问的角色
     *
     * @param roleId  角色标识
     * @param deptIds 部门标识
     */
    boolean grantRole(String roleId, List<String> deptIds);

    /**
     * 授予用户可访问的角色
     * @param deptId 部门标识
     * @param roleIds  角色标识
     * @return 是否授权成功
     */
    boolean grantRoles(String deptId, List<String> roleIds);

    /**
     * 取消授权用户可访问的角色
     *
     * @param roleId  角色标识
     * @param deptIds 部门标识
     */
    boolean unGrantRole(String roleId, List<String> deptIds);

    /**
     * 授予用户可访问的角色
     * @param deptId 部门标识
     * @param roleIds  角色标识
     * @return 是否授权成功
     */
    boolean unGrantRoles(String deptId, List<String> roleIds);

    /**
     * 验证某个角色是否分配给了指定的用户
     *
     * @param roleId  角色标识
     * @param deptIds 用户列表
     * @return 分配结果
     */
    Map<String, Boolean> roleAssignedToDepts(String roleId, List<String> deptIds);


    /**
     * 获取分配给某个用户的所有角色
     *
     * @param deptId 部门标识
     * @return 应用角色信息
     */
    List<GrantAppRoleVo> grantDeptAppWithRole(String deptId, String appId);

    /**
     * 修改角色的过期时间
     * @param deptId 部门标识
     * @param roleId 角色标识
     * @param expiredTime 过期时间
     * @return 是否成功修改
     */
    boolean updateGrantRoleExpiredTime(String deptId, String roleId, Date expiredTime);

    /**
     * 修改角色的授权范围
     * @param deptId 部门标识
     * @param roleId 角色标识
     * @param scope 授权范围
     * @return 是否成功修改
     */
    boolean updateGrantRoleGrantScope(String deptId, String roleId, int scope);

    /**
     * 获取某个应用中分配给某个用户的所有角色
     *
     * @param appId  应用标识
     * @param deptId 部门标识
     */
    List<SysLoginBindRole> listGrantRoleOfDept(@Nullable String appId, String deptId);

    boolean exists(Wrapper<SysDept> eq);

    Page<VSysDeptMemberUser> pageMember(VSysDeptMemberUserQueryVo query);

    Boolean followDeptOfUsers(String deptId, List<String> userIds);

    Boolean unFollowDeptOfUsers(String deptId, List<String> userIds);

    /**
     * 返回已经进行职务兼任的用户
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 确实有兼任某些职务的用户
     */
    List<String> validateUserFollowStatus(String deptId, List<String> userIds);


    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);
}