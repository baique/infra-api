package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.pojo.vo.MenuType;
import tech.hljzj.framework.pojo.vo.Router;
import tech.hljzj.framework.pojo.vo.RouterMeta;
import tech.hljzj.framework.security.SystemUser;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.service.IRouterService;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.app.AppScopeHolder;
import tech.hljzj.framework.util.tree.TreeUtil;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.config.TreeCodeGenerate;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.domain.SysRoleMenu;
import tech.hljzj.infrastructure.mapper.SysMenuMapper;
import tech.hljzj.infrastructure.mapper.SysRoleMenuMapper;
import tech.hljzj.infrastructure.service.SysMenuService;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static tech.hljzj.infrastructure.code.AppConst.TREE_DELIMITER;


/**
 * 菜单管理 sys_menu
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService, IRouterService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SortService sortService;
    @Autowired
    private TreeCodeGenerate treeCodeGenerate;

    @Override
    public SysMenu entityGet(SysMenu entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysMenu entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysMenu entity) {
        if (baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getOwnerAppId, entity.getOwnerAppId())
                .eq(SysMenu::getKey, entity.getKey())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "菜单标识"));
        }

        if (baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getOwnerAppId, entity.getOwnerAppId())
                .eq(SysMenu::getName, entity.getName())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "菜单名称"));
        }

        entity.setNodeKey(treeCodeGenerate.newMenuCode());
        String parentNodePath;
        if (StrUtil.equals("0", entity.getParentId())) {
            parentNodePath = String.join(TREE_DELIMITER, entity.getNodeKey());
        } else {
            SysMenu parentNode = getById(entity.getParentId());
            parentNodePath = String.join(TREE_DELIMITER, parentNode.getNodePath(), parentNode.getNodeKey());
        }
        entity.setNodePath(parentNodePath);
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysMenu entity) {
        SysMenu existsEntity = getById(entity.getId());
        if (baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getOwnerAppId, existsEntity.getOwnerAppId())
                .eq(SysMenu::getKey, entity.getKey())
                .ne(SysMenu::getId, entity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "菜单标识"));
        }

        if (baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getOwnerAppId, existsEntity.getOwnerAppId())
                .eq(SysMenu::getName, entity.getName())
                .ne(SysMenu::getId, entity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "菜单名称"));
        }
        existsEntity.updateForm(entity);

        String prevNodeParent = existsEntity.getParentId();
        String newNodeParent = entity.getParentId();

        boolean parentNodeHasChange = !prevNodeParent.equals(newNodeParent);

        if (parentNodeHasChange) {
            String newParentNodePath;
            if (StrUtil.equals("0", entity.getParentId())) {
                newParentNodePath = String.join(TREE_DELIMITER, existsEntity.getNodeKey());
            } else {
                newParentNodePath = getById(entity.getParentId()).getNodePath();
            }
            String currentOldNodePath = String.join(TREE_DELIMITER, existsEntity.getNodePath());
            String currentNewNodePath = String.join(TREE_DELIMITER, newParentNodePath, existsEntity.getNodeKey());
            existsEntity.setNodePath(currentNewNodePath);

            // 批量更新全部后代节点，指向新的前置节点
            baseMapper.updateNodePath(currentOldNodePath, currentNewNodePath);
        }

        return updateById(existsEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityDelete(SysMenu entity) {
        // 有子菜单禁止删除
        if (baseMapper.exists(Wrappers.<SysMenu>query().eq("parent_id_", entity.getId()))) {
            throw UserException.defaultError("如要删除菜单必须先删除其所有下级菜单");
        }
        // 同时删除菜单授予的角色
        roleMenuMapper.delete(Wrappers.<SysRoleMenu>query().eq("menu_id_", entity.getId()));
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        listByIds(ids).forEach(this::entityDelete);
        return true;
    }


    @Override
    public Page<SysMenu> page(SysMenuQueryVo query) {
        Page<SysMenu> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }


    @Override
    public List<SysMenu> list(SysMenuQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
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
                        .eq(SysMenu::getOwnerAppId, condition.row().getOwnerAppId())
                        .eq(SysMenu::getParentId, condition.row().getParentId())
                        .orderByAsc(SysMenu::getSort)
                        .orderByDesc(SysMenu::getCreateTime)
                        .orderByDesc(SysMenu::getId)
        ));
    }

    @Override
    public List<Router> getRouters() {
        TokenAuthentication authentication = AuthUtil.getAuthentication();
        UserInfo u = authentication.getPrincipal().getUserInfo();
        SysMenuQueryVo queryVo = new SysMenuQueryVo();
        queryVo.setOwnerAppId(StrUtil.blankToDefault(u.getAppId(), "0"));
        boolean isSuperSuperSuperSuperSuperVeryVeryVeryVeryVeryVeryNbClassAdmin = u.getRole().contains(SystemUser.GLOBAL_PERM);
        if (!isSuperSuperSuperSuperSuperVeryVeryVeryVeryVeryVeryNbClassAdmin) {
            queryVo.setKeyIn(authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(authority -> !authority.startsWith("ROLE_")).collect(Collectors.toList()));
            if (CollUtil.isEmpty(queryVo.getKeyIn())) {
                return Collections.emptyList();
            }
        }

        queryVo.setMenuTypeIn(Arrays.asList(
                MenuType.DIR,
                MenuType.MENU,
                MenuType.LINK
        ));
        List<SysMenu> menuList = list(queryVo);
        return TreeUtil.listToTree(menuList.stream().map(m -> {
            Router r = new Router();
            r.setId(m.getId());
            r.setParentId(m.getParentId());
            r.setType(m.getMenuType());
            r.setName(m.getKey());
            r.setHideMenu(ObjUtil.equals(AppConst.NOT, m.getVisible()));
            r.setDisabled(ObjUtil.equals(AppConst.NOT, m.getStatus()));
            String path = StrUtil.blankToDefault(m.getPath(), m.getKey());

            RouterMeta rm = new RouterMeta();
            rm.setTitle(m.getName());
            rm.setIcon(m.getIcon());
            if (MenuType.LINK.equals(m.getMenuType())) {
                rm.setFrameSrc(m.getPath());
                r.setPath("/link/" + r.getId());
            } else {
                r.setPath(StrUtil.addPrefixIfNot(path, "/"));
                r.setComponent(m.getComponent());
            }
            // 这里其实有个分支，那么就是是否要有菜单权限的问题
            r.setMeta(rm);
            return r;
        }).collect(Collectors.toList()));
    }
}