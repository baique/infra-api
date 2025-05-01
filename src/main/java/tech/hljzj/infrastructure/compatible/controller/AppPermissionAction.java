package tech.hljzj.infrastructure.compatible.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.compatible.controller.bsae.MController;
import tech.hljzj.infrastructure.compatible.controller.bsae.R;
import tech.hljzj.infrastructure.compatible.util.AppHelper;
import tech.hljzj.infrastructure.compatible.vo.bean.PageUtil;
import tech.hljzj.infrastructure.compatible.vo.permission.Permission;
import tech.hljzj.infrastructure.compatible.vo.permission.PermissionTree;
import tech.hljzj.infrastructure.compatible.vo.role.Role;
import tech.hljzj.infrastructure.compatible.vo.user.userDetails.UserDetails;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysRoleMenu;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * app权限接口
 *
 * @author wa\
 */
@RequestMapping("/app")
@RestController
@Anonymous
public class AppPermissionAction extends MController {

    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;
    private final SysRoleMenuService sysRoleMenuService;

    public AppPermissionAction(SysMenuService sysMenuService, SysRoleService sysRoleService, SysUserService sysUserService, SysRoleMenuService sysRoleMenuService) {
        super();
        this.sysMenuService = sysMenuService;
        this.sysRoleService = sysRoleService;
        this.sysUserService = sysUserService;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @PostMapping("/permission/hasPermissionUsers")
    @ResponseBody
    public Object findHasPermissionUsers(Permission permission) {
        SysMenuQueryVo q = permission.toQuery();
        q.setOwnerAppId(AppHelper.getLoginApp(request));
        List<SysMenu> byPermission = sysMenuService.list(q);
        if (CollUtil.isEmpty(byPermission)) {
            return R.fail().setMsg("未找到权限数据");
        }
        List<SysRoleMenu> roleIds = sysRoleMenuService.list(Wrappers
                .<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getMenuId, byPermission.stream().map(SysMenu::getId).collect(Collectors.toList()))
        );
        if (CollUtil.isEmpty(byPermission)) {
            return R.fail().setMsg("未找到权限数据");
        }


        VSysUserQueryVo queryVo = new VSysUserQueryVo();
        queryVo.setHasRole(roleIds.stream().map(SysRoleMenu::getRoleId).collect(Collectors.toList()));
        return success(sysUserService.list(queryVo).stream().map(UserDetails::from).collect(Collectors.toList()));
//        return null;
    }


    /*
     * 查询所有权限
     * */

    @PostMapping("/permission/page")
    @ResponseBody
    public Object pageAll(Permission ps, PageUtil page) {
        SysMenuQueryVo query = ps.toQuery();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        long size = 0;
        List<SysMenu> menuList;
        if (page.isPage()) {
            query.setPageNum(page.getPage());
            query.setPageSize(page.getLimit());
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysMenu> pageContent = sysMenuService.page(query);
            menuList = pageContent.getRecords();
            size = pageContent.getTotal();
        } else {
            menuList = sysMenuService.list(query);
            size = menuList.size();
        }
        return success(menuList.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", size);
    }

    /**
     * 查看全部权限列表，无分页
     */

    @PostMapping("/permission/all")
    @ResponseBody
    public Object findAll(Permission ps) {
        PageUtil pu = new PageUtil();
        pu.setUsePage(false);
        return pageAll(ps, pu);
    }


    /*
     * 根据角色ID 应用ID 获取关联权限
     */

    @PostMapping("/permissionByRole")
    @ResponseBody
    private Object findRolePermissions(Role roleInfo) {
        List<SysRole> roleList = sysRoleService.list(roleInfo.toQuery());
        if (CollUtil.isEmpty(roleList)) {
            return success(Collections.emptyList());
        }
        List<SysMenu> sysMenus = sysRoleService.listGrantOfRoles(
                roleList.stream().map(SysRole::getId).collect(Collectors.toList()),
                AppHelper.getLoginApp(request)
        );
        return success(sysMenus.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", sysMenus.size());
    }


    /*
     * 根据父权限查询所有子权限（列表）
     */
//
    @PostMapping("/findByPermissionParent")
    @ResponseBody
    private Object findByPermissionParent(String permissionInfo) {
        SysMenuQueryVo query = new SysMenuQueryVo();
        query.setId(permissionInfo);
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        SysMenu menu = sysMenuService.getOne(query.buildQueryWrapper(), false);
        if (menu != null) {
            SysMenuQueryVo treeQuery = new SysMenuQueryVo();
            treeQuery.setNodePathPrefix(menu.getNodePath());
            return success(sysMenuService.list(treeQuery).stream().map(Permission::from).collect(Collectors.toList()));
        }
        return success(Collections.emptyList());
    }


    /*
     * 根据父权限查询所有子权限（树形）
     */
    @PostMapping("/findByPermissionParentTree")
    @ResponseBody
    private Object findByPermissionParentTree(Permission permissionInfo) {
        String parentNodePath;
        String idNot = null;
        if (StrUtil.isBlank(permissionInfo.getPermissionId())) {
            parentNodePath = null;
        } else {
            SysMenu parentMenu = sysMenuService.getById(permissionInfo.getPermissionId());
            if (parentMenu == null) {
                return success(Collections.emptyList()).setMsg("未找到相关数据");
            }
            idNot = parentMenu.getId();
            parentNodePath = String.join(AppConst.TREE_DELIMITER, parentMenu.getNodePath());
        }
        permissionInfo.setPermissionId(null);

        SysMenuQueryVo q = permissionInfo.toQuery();
        q.setIdNot(idNot);
        q.setNodePathPrefix(parentNodePath);
        q.setOwnerAppId(AppHelper.getLoginApp(request));

        List<SysMenu> nextLevelMenus = sysMenuService.list(q);
        return success(PermissionTree.from(nextLevelMenus));
    }


    /*
     * 查询用户拥有的该父权限的子权限列表（列表）
     */
    @PostMapping("/findByPermissionParentAndUser")
    @ResponseBody
    private Object findByPermissionParentAndUser(String userId, String permissionId) {
        String appId = AppHelper.getLoginApp(request);

        String parentNodePath, idNot;
        if (StrUtil.isBlank(permissionId)) {
            parentNodePath = null;
            idNot = null;
        } else {
            SysMenu parentMenu = sysMenuService.getById(permissionId);
            if (parentMenu == null) {
                return success(Collections.emptyList()).setMsg("未找到相关数据");
            }
            parentNodePath = parentMenu.getNodePath();
            idNot = parentMenu.getId();
        }


        List<SysRole> roleList = sysUserService.listGrantRoleOfUser(userId, appId);
        if (CollUtil.isEmpty(roleList)) {
            return R.fail().setMsg("未找到相关数据");
        }
        // 查询当前用户的，属于某个父级的权限……
        List<SysMenu> menus = sysRoleService.listGrantOfRoles(
                roleList.stream()
                        .map(SysRole::getId)
                        .collect(Collectors.toList()),
                appId,
                f -> f.ne(idNot != null, SysMenu::getId, idNot).likeRight(StrUtil.isNotBlank(parentNodePath), SysMenu::getNodePath, parentNodePath)
        );

        // 这里是需要在内存中进行动作的完成吗？
        return success(menus.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", menus.size());
    }


    /*
     * 查询用户拥有的该父权限的子权限列表（树形）
     */
    @PostMapping("/findByPermissionParentAndUserTree")
    @ResponseBody
    private Object findByPermissionParentAndUserTree(String userId, String permissionId) {
        String appId = AppHelper.getLoginApp(request);

        String parentNodePath, idNot;
        if (StrUtil.isBlank(permissionId)) {
            parentNodePath = null;
            idNot = null;
        } else {
            SysMenu parentMenu = sysMenuService.getById(permissionId);
            if (parentMenu == null) {
                return success(Collections.emptyList()).setMsg("未找到相关数据");
            }
            parentNodePath = parentMenu.getNodePath();
            idNot = parentMenu.getId();
        }

        List<SysRole> roleList = sysUserService.listGrantRoleOfUser(userId, appId);
        if (CollUtil.isEmpty(roleList)) {
            return success(Collections.emptyList()).setMsg("未找到相关数据");
        }

        // 查询当前用户的，属于某个父级的权限……
        List<SysMenu> menus = sysRoleService.listGrantOfRoles(roleList.stream()
                        .map(SysRole::getId)
                        .collect(Collectors.toList()), appId,
                f -> f
                        .ne(idNot != null, SysMenu::getId, idNot)
                        .likeRight(StrUtil.isNotBlank(parentNodePath), SysMenu::getNodePath, parentNodePath
                        )
        );

        List<Permission> ms = PermissionTree.from(menus);
        return success(ms).addProperty("count", ms.size());
    }


    /*
     * 查询用户的所有权限(列表)
     */
    @PostMapping("/findPermissionByUser")
    @ResponseBody
    private Object findPermissionByUser(String userId) {
        String appId = AppHelper.getLoginApp(request);
        List<SysRole> roleList = sysUserService.listGrantRoleOfUser(userId, appId);
        if (CollUtil.isEmpty(roleList)) {
            return success(Collections.emptyList()).setMsg("未找到相关数据");
        }
        // 查询当前用户的，属于某个父级的权限……
        List<SysMenu> menus = sysRoleService.listGrantOfRoles(roleList.stream()
                .map(SysRole::getId)
                .collect(Collectors.toList()), appId, f -> f);
        return success(menus.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", menus.size());
    }


    /*
     * 查询角色拥有的该父权限ID下的所有权限子权限(列表)
     */
    @PostMapping("/findPermissionByURole")
    @ResponseBody
    private Object findPermissionByURole(String roleId, String permissionId) {
        String parentNodePath, idNot;
        if (StrUtil.isBlank(permissionId)) {
            parentNodePath = null;
            idNot = null;
        } else {
            SysMenu parentMenu = sysMenuService.getById(permissionId);
            if (parentMenu == null) {
                return success(Collections.emptyList()).setMsg("未找到相关数据");
            }
            parentNodePath = parentMenu.getNodePath();
            idNot = parentMenu.getId();
        }

        List<SysMenu> menus = sysRoleService.listGrantOfRoles(Collections.singleton(roleId), AppHelper.getLoginApp(request),
                f -> f
                        .ne(idNot != null, SysMenu::getId, idNot)
                        .likeRight(StrUtil.isNotBlank(parentNodePath), SysMenu::getNodePath, parentNodePath)
        );
        return success(menus.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", menus.size());
    }


    /*
     * 查询角色拥有的该父权限ID下的所有权限子权限(树形)
     */
    @PostMapping("/findPermissionByURoleTree")
    @ResponseBody
    private Object findPermissionByURoleTree(String roleId, String permissionId) {
        String parentNodePath, idNot;
        if (StrUtil.isBlank(permissionId)) {
            parentNodePath = null;
            idNot = null;
        } else {
            SysMenu parentMenu = sysMenuService.getById(permissionId);
            if (parentMenu == null) {
                return success(Collections.emptyList()).setMsg("未找到相关数据");
            }
            parentNodePath = parentMenu.getNodePath();
            idNot = parentMenu.getId();
        }

        return success(PermissionTree.from(sysRoleService.
                listGrantOfRoles(Collections.singleton(roleId),
                        AppHelper.getLoginApp(request),
                        f -> f
                                .ne(idNot != null, SysMenu::getId, idNot)
                                .likeRight(StrUtil.isNotBlank(parentNodePath), SysMenu::getNodePath, parentNodePath)))
        );
    }


    /*
     * 查询角色的所有权限(列表)
     */
    @PostMapping("/findPermissionByUserAll")
    @ResponseBody
    private Object findPermissionByUserAll(String roleId) {
        return success(sysRoleService
                .listGrantOfRole(roleId, AppHelper.getLoginApp(request))
                .stream().map(Permission::from).collect(Collectors.toList())
        );
    }


    /*
     * 查询角色的所有权限(树形)
     */
//
    @PostMapping("/findPermissionByUserAllTree")
    @ResponseBody
    private Object findPermissionByUserAllTree(String roleId) {
        return success(PermissionTree.from(sysRoleService.listGrantOfRole(roleId, AppHelper.getLoginApp(request))));
    }


    /*
     * 查询该应用下的所有权限(列表)
     */
    @PostMapping("/findPermissionAll")
    @ResponseBody
    private Object findPermissionAll() {
        SysMenuQueryVo query = new SysMenuQueryVo();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        List<SysMenu> data = sysMenuService.list(query);

        return success(data.stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", data.size());
    }


    /*
     * 查询该应用下的所有权限(树形)
     */
    @PostMapping("/findPermissionAllTree")
    @ResponseBody
    private Object findPermissionAllTree() {
        SysMenuQueryVo query = new SysMenuQueryVo();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        List<SysMenu> data = sysMenuService.list(query);
        //转换为树形
        return success(PermissionTree.from(data));
    }


    /*
     * 查询该应用下的所有权限(树形)（动态条件）
     */
    @PostMapping("/findPermissionAllConditionsTree")
    @ResponseBody
    private Object findPermissionAllConditionsTree(Permission ps) {
        SysMenuQueryVo query = ps.toQuery();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        List<SysMenu> list = sysMenuService.list(query);
        return success(PermissionTree.from(list));
    }


    /*
     * 查询该应用下的所有权限(列表)（动态条件）
     */
    @PostMapping("/findPermissionAllConditions")
    @ResponseBody
    private Object findPermissionAllConditions(Permission ps) {
        SysMenuQueryVo query = ps.toQuery();
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        List<SysMenu> list = sysMenuService.list(query);
        return success(list.stream().map(Permission::from).collect(Collectors.toList()));
    }


    /*
     * 查询该应用下的所有权限(列表)（分页）
     */
    @PostMapping("/findPermissionByPage")
    @ResponseBody
    private Object findPermissionByPage(Permission ps, PageUtil page) {
        return findPermissionByPageConditions(ps, page);
    }


    /*
     * 查询该应用下的所有权限(列表)（分页）(动态条件)
     */
    @PostMapping("/findPermissionByPageConditions")
    @ResponseBody
    private Object findPermissionByPageConditions(Permission ps, PageUtil page) {
        SysMenuQueryVo query = ps.toQuery();
        query.setPageNum(page.getPage());
        query.setPageSize(page.getLimit());
        query.setOwnerAppId(AppHelper.getLoginApp(request));
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysMenu> pageData = sysMenuService.page(query);
        return success(pageData.getRecords().stream().map(Permission::from).collect(Collectors.toList())).addProperty("count", pageData.getTotal());
    }


    /*
     * 鉴定该用户是否拥有该权限
     */
    @PostMapping("/IdentificationOfPermissions")
    @ResponseBody
    private Object IdentificationOfPermissions(String userId, String permissionId) {
        List<SysRole> roleList = sysUserService.listGrantRoleOfUser(userId, AppHelper.getLoginApp(request));
        if (CollUtil.isEmpty(roleList)) {
            return success(false);
        }
        return success(sysRoleMenuService.getOne(Wrappers.<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleList.stream().map(SysRole::getId).collect(Collectors.toList()))
                .eq(SysRoleMenu::getMenuId, permissionId)
        ) != null);
    }


    /*
     * 根据permissionValue查询permission对象
     */
    @PostMapping("/findByPermissionValue")
    @ResponseBody
    private Object findByPermissionValue(String permissionvalue) {
        List<SysMenu> c = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getOwnerAppId, AppHelper.getLoginApp(request))
                .eq(SysMenu::getKey, permissionvalue)
        );
        return success(c.stream().map(Permission::from)).addProperty("count", c.size());
    }
}
