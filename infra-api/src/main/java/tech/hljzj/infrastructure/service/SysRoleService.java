package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 角色管理 sys_role
 * 业务接口
 *
 * @author wa
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysRole entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysRole entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysRole entity);

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
    SysRole entityGet(SysRole entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysRole entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysRole> page(SysRoleQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysRole> list(SysRoleQueryVo query);


    /**
     * 授权
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     * @param menuId 菜单标识
     */
    void grant(String roleId, String appId, List<String> menuId);

    /**
     * 取消授权
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     * @param menuId 菜单标识
     */
    void unGrant(String roleId, String appId, List<String> menuId);

    /**
     * 获取角色在某应用下可访问的菜单列表
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     * @return 可访问的菜单列表
     */
    List<SysMenu> listGrantOfRole(String roleId, String appId);


    /**
     * 获取授予角色的权限
     *
     * @param roleId 角色列表
     * @param appId  应用标识
     * @return 获取授予某些角色的菜单权限
     */
    default List<SysMenu> listGrantOfRoles(Collection<String> roleId, String appId) {
        return this.listGrantOfRoles(roleId, appId, f -> f);
    }

    /**
     * 获取授予角色的权限
     *
     * @param roleId 角色列表
     * @param appId  应用标识
     * @return 获取授予某些角色的菜单权限
     */
    List<SysMenu> listGrantOfRoles(Collection<String> roleId, String appId, Function<LambdaQueryWrapper<SysMenu>, ? extends Wrapper<SysMenu>> consumer);


    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    @Transactional(rollbackFor = Exception.class)
    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);
}