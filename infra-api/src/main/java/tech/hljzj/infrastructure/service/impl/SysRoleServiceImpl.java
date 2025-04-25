package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysRoleMenu;
import tech.hljzj.infrastructure.domain.SysUserRole;
import tech.hljzj.infrastructure.mapper.SysRoleMapper;
import tech.hljzj.infrastructure.mapper.SysRoleMenuMapper;
import tech.hljzj.infrastructure.service.SysMenuService;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.service.SysUserRoleService;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 角色管理 sys_role
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SortService sortService;

    @Override
    public SysRole entityGet(SysRole entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysRole entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysRole entity) {
        if (baseMapper.exists(Wrappers.lambdaQuery(SysRole.class)
                .eq(SysRole::getKey, entity.getKey())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "角色标识"));
        }
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysRole entity) {
        SysRole existsEntity = getById(entity.getId());
        if (baseMapper.exists(Wrappers.lambdaQuery(SysRole.class)
                .eq(SysRole::getKey, entity.getKey())
                .ne(SysRole::getId, entity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "角色标识"));
        }
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityDelete(SysRole entity) {
        //如果授权给了用户
        if (sysUserRoleService.exists(Wrappers
                .<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, entity.getId())
        )) {
            throw UserException.defaultError("如要删除角色必须先取消授予所有用户。");
        }
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        listByIds(ids).forEach(this::entityDelete);
        return true;
    }


    @Override
    public Page<SysRole> page(SysRoleQueryVo query) {
        Page<SysRole> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysRole> list(SysRoleQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unGrant(String roleId, String appId, List<String> menuId) {
        // 取消授权
        menuId.stream().map(f -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setAppId(appId);
            roleMenu.setMenuId(f);
            return roleMenu;
        }).forEach(roleMenu -> roleMenuMapper.delete(Wrappers.query(roleMenu)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grant(String roleId, String appId, List<String> menuId) {
        //保存用户选中的菜单列表
        menuId.stream().map(f -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setAppId(appId);
            roleMenu.setMenuId(f);
            return roleMenu;
        }).forEach(roleMenu -> roleMenuMapper.insert(roleMenu));
    }

    @Override
    public List<SysMenu> listGrantOfRole(String roleId, String appId) {
        SysMenuQueryVo queryVo = new SysMenuQueryVo();
        LambdaQueryWrapper<SysMenu> queryWrapper = queryVo.buildQueryWrapper();
        //获取角色已经授权使用的菜单
        List<String> menuIds = roleMenuMapper.selectList(Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId)
                .eq(SysRoleMenu::getAppId, appId)
        ).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        if (CollUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        queryWrapper.orderByAsc(SysMenu::getSort);
        queryWrapper.orderByDesc(SysMenu::getCreateTime);
        queryWrapper.orderByDesc(SysMenu::getId);
        //检索所有已授权访问的菜单
        return menuService.list(queryWrapper.in(SysMenu::getId, menuIds));

    }


    @Override
    public List<SysMenu> listGrantOfRoles(Collection<String> roleId, String appId, Function<LambdaQueryWrapper<SysMenu>, ? extends Wrapper<SysMenu>> consumer) {
        if (CollUtil.isEmpty(roleId)) {
            return Collections.emptyList();
        }
        SysMenuQueryVo queryVo = new SysMenuQueryVo();
        LambdaQueryWrapper<SysMenu> queryWrapper = queryVo.buildQueryWrapper();
        //获取角色已经授权使用的菜单
        List<String> menuIds = roleMenuMapper.selectList(Wrappers.<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleId)
                .eq(SysRoleMenu::getAppId, appId)
        ).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        if (CollUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        queryWrapper.orderByAsc(SysMenu::getSort);
        queryWrapper.orderByDesc(SysMenu::getCreateTime);
        queryWrapper.orderByDesc(SysMenu::getId);
        //检索所有已授权访问的菜单
        return menuService.list(consumer.apply(queryWrapper.in(SysMenu::getId, menuIds)));
    }


    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityUpdateSort(String rowId, String prevRowId, String nextRowId) {
        updateBatchById(sortService.applySort(
                rowId,
                prevRowId,
                nextRowId,
                baseMapper,
                (condition) -> condition.where()
                        .eq(SysRole::getOwnerAppId, condition.row().getOwnerAppId())
                        .orderByAsc(SysRole::getSort)
                        .orderByDesc(SysRole::getCreateTime)
                        .orderByDesc(SysRole::getId)
        ));
    }
}