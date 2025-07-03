package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.mapper.SysAppMapper;
import tech.hljzj.infrastructure.mapper.SysConfigMapper;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.SysApp.SysAppExport;
import tech.hljzj.infrastructure.vo.SysApp.SysAppQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 应用管理 sys_app
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {

    public static final String CAHCE_NAME = "sys:app";
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysMenuService sysMenuService;
    private final SysConfigService sysConfigService;
    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService sysDictTypeService;


    public SysAppServiceImpl(SysUserRoleService sysUserRoleService, SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService, SysMenuService sysMenuService, SysConfigMapper sysConfigMapper, SysConfigService sysConfigService, SysDictDataService sysDictDataService, SysDictTypeService sysDictTypeService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysMenuService = sysMenuService;
        this.sysConfigService = sysConfigService;
        this.sysDictDataService = sysDictDataService;
        this.sysDictTypeService = sysDictTypeService;
    }

    @Override
    public SysApp entityGet(SysApp entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    @Cacheable(cacheNames = CAHCE_NAME, key = "#id")
    public SysApp entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    @CacheEvict(cacheNames = CAHCE_NAME, key = "#p0.id")
    public boolean entityCreate(SysApp entity) {
        if (baseMapper.exists(Wrappers.lambdaQuery(SysApp.class)
            .eq(SysApp::getKey, entity.getKey())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "应用标识"));
        }
        if (baseMapper.exists(Wrappers.lambdaQuery(SysApp.class)
            .eq(SysApp::getName, entity.getName())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "应用名称"));
        }
        return save(entity);
    }


    @Override
    @CacheEvict(cacheNames = CAHCE_NAME, key = "#p0.id")
    public boolean entityUpdate(SysApp entity) {
        SysApp existsEntity = getById(entity.getId());
        if (baseMapper.exists(Wrappers.lambdaQuery(SysApp.class)
            .eq(SysApp::getKey, entity.getKey())
            .ne(SysApp::getId, existsEntity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "应用标识"));
        }
        if (baseMapper.exists(Wrappers.lambdaQuery(SysApp.class)
            .eq(SysApp::getName, entity.getName())
            .ne(SysApp::getId, existsEntity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "应用名称"));
        }
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CAHCE_NAME, key = "#p0.id")
    public boolean entityDelete(SysApp entity) {
        //如果需要删除应用，那么必须保证该应用下的数据已经完全无用
        if (sysUserRoleService.exists(Wrappers
            .<SysUserRole>lambdaQuery()
            .eq(SysUserRole::getAppId, entity.getId())
        )) {
            throw UserException.defaultError("如要删除应用必须先清空应用中已授予用户的角色、菜单信息。");
        }

        //删除角色菜单关联
        sysRoleMenuService.remove(Wrappers.<SysRoleMenu>query().eq("app_id_", entity.getId()));
        //删除角色
        sysRoleService.remove(Wrappers.<SysRole>query().eq("owner_app_id_", entity.getId()));
        //删除菜单
        sysMenuService.remove(Wrappers.<SysMenu>query().eq("owner_app_id_", entity.getId()));
        //最后删除应用
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CAHCE_NAME, allEntries = true)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        listByIds(ids).forEach(this::entityDelete);
        return true;
    }


    @Override
    public Page<SysApp> page(SysAppQueryVo query) {
        Page<SysApp> pageConfig = query.buildPagePlus();
        // add default order

        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysApp> list(SysAppQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }


    @Override
    public SysAppExport exportAppData(String appId) {
        SysApp appInfo = getById(appId);
        List<SysConfig> config = sysConfigService.list(Wrappers.<SysConfig>lambdaQuery()
            .eq(SysConfig::getOwnerAppId, appId)
        );

        List<SysDictType> dictTypes = sysDictTypeService.list(Wrappers.<SysDictType>lambdaQuery()
            .eq(SysDictType::getOwnerAppId, appId)
        );

        List<SysDictData> dictData = sysDictDataService.list(Wrappers.<SysDictData>lambdaQuery()
            .eq(SysDictData::getOwnerAppId, appId)
        );


        List<SysRole> role = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
            .eq(SysRole::getOwnerAppId, appId)
        );
        List<SysMenu> menu = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
            .eq(SysMenu::getOwnerAppId, appId)
        );
        List<SysRoleMenu> roleMenuGrantList = CollUtil.isNotEmpty(role) ? sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
            .in(SysRoleMenu::getRoleId, role.stream().map(SysRole::getId).collect(Collectors.toList()))
        ) : Collections.emptyList();
        // 传输为一组加密的json数据
        SysAppExport export = new SysAppExport();
        export.setSysApp(appInfo);
        export.setConfigList(config);
        export.setRoleList(role);
        export.setMenuList(menu);
        export.setRoleMenuGrantList(roleMenuGrantList);
        export.setDictTypeList(dictTypes);
        export.setDictDataList(dictData);
        return export;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CAHCE_NAME, key = "#p0.sysApp.id")
    public void importData(SysAppExport data) {
        SysApp app = data.getSysApp();
        SysApp existsApp = getById(app.getId());
        if (existsApp == null) {
            super.save(app);
        }
        sysConfigService.saveOrUpdateBatch(data.getConfigList());
        sysMenuService.saveOrUpdateBatch(data.getMenuList());
        sysRoleService.saveOrUpdateBatch(data.getRoleList());
        sysRoleMenuService.saveOrUpdateBatch(data.getRoleMenuGrantList());
        sysDictTypeService.saveOrUpdateBatch(data.getDictTypeList());
        sysDictDataService.saveOrUpdateBatch(data.getDictDataList());
    }
}