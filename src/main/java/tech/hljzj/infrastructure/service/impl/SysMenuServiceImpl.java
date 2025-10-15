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
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.pojo.vo.MenuType;
import tech.hljzj.framework.pojo.vo.Router;
import tech.hljzj.framework.pojo.vo.RouterMeta;
import tech.hljzj.framework.security.SystemUser;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.service.IRouterService;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.excel.ExcelUtil;
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
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuListVo;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.text.StrFormatter.format;
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
    private SysRoleMenuMapper sysRoleMenuMapper;
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
            .eq(SysMenu::getParentId, entity.getParentId())
            .eq(SysMenu::getName, entity.getName())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "菜单名称"));
        }

        entity.setNodeKey(treeCodeGenerate.newMenuCode());
        String parentNodePath;
        if (StrUtil.equals("0", entity.getParentId())) {
            parentNodePath = TREE_DELIMITER + entity.getNodeKey();
        } else {
            SysMenu parentNode = getById(entity.getParentId());
            parentNodePath = String.join(TREE_DELIMITER, parentNode.getNodePath(), entity.getNodeKey());
        }
        entity.setNodePath(parentNodePath);
        return save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityCreateGroup(String menuKey, String menuName, String ownerAppId) {
        SysMenu group = new SysMenu();
        group.setKey(menuKey + ":list");
        group.setName(menuName + "管理");
        group.setParentId("0");
        group.setOwnerAppId(ownerAppId);
        group.setStatus("1");
        group.setVisible("1");
        group.setMenuType("F");
        group.setPath("/" + menuKey);
        this.entityCreate(group);

        SysMenu detail = new SysMenu();
        detail.setKey(menuKey + ":query");
        detail.setName(menuName + "详情");
        detail.setParentId(group.getId());
        detail.setOwnerAppId(ownerAppId);
        detail.setStatus("1");
        detail.setVisible("1");
        detail.setMenuType("B");
        detail.setSort(100);

        this.entityCreate(detail);

        SysMenu insert = new SysMenu();
        insert.setKey(menuKey + ":add");
        insert.setName(menuName + "新增");
        insert.setParentId(group.getId());
        insert.setOwnerAppId(ownerAppId);
        insert.setStatus("1");
        insert.setVisible("1");
        insert.setMenuType("B");
        insert.setSort(200);

        this.entityCreate(insert);

        SysMenu update = new SysMenu();
        update.setKey(menuKey + ":edit");
        update.setName(menuName + "修改");
        update.setParentId(group.getId());
        update.setOwnerAppId(ownerAppId);
        update.setStatus("1");
        update.setVisible("1");
        update.setMenuType("B");
        update.setSort(300);

        this.entityCreate(update);

        SysMenu delete = new SysMenu();
        delete.setKey(menuKey + ":delete");
        delete.setName(menuName + "删除");
        delete.setParentId(group.getId());
        delete.setOwnerAppId(ownerAppId);
        delete.setStatus("1");
        delete.setVisible("1");
        delete.setMenuType("B");
        delete.setSort(400);

        this.entityCreate(delete);

        SysMenu export = new SysMenu();
        export.setKey(menuKey + ":export");
        export.setName(menuName + "导出");
        export.setParentId(group.getId());
        export.setOwnerAppId(ownerAppId);
        export.setStatus("1");
        export.setVisible("1");
        export.setMenuType("B");
        export.setSort(500);

        this.entityCreate(export);
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
            .eq(SysMenu::getParentId, entity.getParentId())
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
                newParentNodePath = "";
            } else {
                newParentNodePath = getById(entity.getParentId()).getNodePath();
            }
            String currentOldNodePath = existsEntity.getNodePath();
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
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().eq("menu_id_", entity.getId()));
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

    @Override
    public List<ExcelUtil.FailRowWrap<SysMenuListVo>> importData(String appId, MultipartFile file) throws IOException {
        List<SysMenuListVo> vos = new ArrayList<>();
        List<ExcelUtil.FailRowWrap<SysMenuListVo>> d = ExcelUtil.readExcel(ExcelUtil.getType(file.getOriginalFilename()), file.getInputStream(), SysMenuListVo.class, vos::add);
        // 这里需要进行保存
        Map<String, SysMenuListVo> ref = new HashMap<>();
        for (SysMenuListVo vo : vos) {
            ref.put(vo.getKey(), vo);
        }
        // 开始保存
        for (SysMenuListVo vo : vos) {
            vo.setOwnerAppId(appId);
            saveAndRecord(vo, ref, new LinkedHashSet<>());
        }
        return d;
    }

    private void saveAndRecord(SysMenuListVo currentNode, Map<String, SysMenuListVo> nodeMapping, Set<String> link) {
        String parentKey = currentNode.getParentKey();
        if (!link.add(parentKey)) {
            throw UserException.defaultError(format(
                "菜单出现循环引用:{}", String.join(">", link)
            ));
        }

        if (StrUtil.isBlank(parentKey)) {
            currentNode.setParentId("0");
        } else {
            // 先看本地检查父级是否存在，如果存在先创建父级，然后移除
            SysMenuListVo d = nodeMapping.get(parentKey);
            if (StrUtil.isBlank(d.getId())) {
                // 先保存上文，递归动作，需要记录链路
                saveAndRecord(d, nodeMapping, link);
            }
            currentNode.setParentId(d.getId());
        }
        // 调用创建
        SysMenu dto = currentNode.toDto();
        this.entityCreate(dto);
        currentNode.setId(dto.getId());
    }

    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityUpdateSort(String rowId, String prevRowId, String nextRowId) {
        List<SysMenu> menus = sortService.applySort(
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
        );
        updateBatchById(menus);
    }

    @Override
    public List<Router> getRouters() {
        TokenAuthentication authentication = AuthUtil.getAuthentication();
        UserInfo u = authentication.getPrincipal().getUserInfo();
        SysMenuQueryVo queryVo = new SysMenuQueryVo();
        queryVo.setOwnerAppId(AppScopeHolder.requiredScopeAppId());

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