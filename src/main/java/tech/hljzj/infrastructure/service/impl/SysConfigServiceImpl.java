package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysConfig;
import tech.hljzj.infrastructure.mapper.SysConfigMapper;
import tech.hljzj.infrastructure.service.SysConfigService;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysConfig.SysConfigQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * 系统配置 sys_config
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SortService sortService;

    public SysConfigServiceImpl(SortService sortService) {
        this.sortService = sortService;
    }

    @Override
    public SysConfig entityGet(SysConfig entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysConfig entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysConfig entity) {
        if (baseMapper.exists(Wrappers.<SysConfig>query().lambda()
                .eq(SysConfig::getKey, entity.getKey())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "标识"));
        }
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysConfig entity) {
        if (baseMapper.exists(Wrappers.<SysConfig>query().lambda()
                .eq(SysConfig::getKey, entity.getKey())
                .ne(SysConfig::getId, entity.getId())

        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "标识"));
        }

        SysConfig existsEntity = getById(entity.getId());
        // 如果数据依然是锁定的
        if (Objects.equals(AppConst.YES, existsEntity.getLocked()) && Objects.equals(existsEntity.getLocked(), entity.getLocked())) {
            throw UserException.defaultError(MsgUtil.t("data.locked", "配置项"));
        }
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysConfig entity) {
        SysConfig existsEntity = entityGet(entity);
        if (Objects.equals(AppConst.YES, existsEntity.getLocked())) {
            throw UserException.defaultError(MsgUtil.t("data.locked", "配置项"));
        }
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return remove(Wrappers.<SysConfig>lambdaQuery()
                .in(SysConfig::getId, ids)
                .ne(SysConfig::getLocked, AppConst.YES)
        );
    }


    @Override
    public Page<SysConfig> page(SysConfigQueryVo query) {
        Page<SysConfig> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysConfig> list(SysConfigQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applySort(String id, String toPrevId, String toNextId) {
        updateBatchById(sortService.applySort(
                id, toPrevId, toNextId,
                baseMapper,
                c -> c.where()
                        .eq(SysConfig::getOwnerAppId, c.row().getOwnerAppId())
                        .orderByAsc(SysConfig::getSort)
                        .orderByDesc(SysConfig::getCreateTime)
                        .orderByDesc(SysConfig::getId)
                ,
                100
        ));
    }

    @Override
    public SysConfig getByKey(String key) {
        String firstQueryAppId = AppScopeHolder.getScopeAppIdOrDefault();
        SysConfig config = getOne(Wrappers
                        .<SysConfig>lambdaQuery()
                        .eq(SysConfig::getKey, key)
                        .eq(SysConfig::getOwnerAppId, firstQueryAppId)
                        .orderByAsc(SysConfig::getSort)
                        .orderByDesc(SysConfig::getCreateTime)
                        .orderByDesc(SysConfig::getId)
                , false);

        if (config == null && !StrUtil.equals(AppConst.ID, firstQueryAppId)) {
            return getOne(Wrappers
                            .<SysConfig>lambdaQuery()
                            .eq(SysConfig::getKey, key)
                            .eq(SysConfig::getOwnerAppId, AppConst.ID)
                            .orderByAsc(SysConfig::getSort)
                            .orderByDesc(SysConfig::getCreateTime)
                            .orderByDesc(SysConfig::getId)
                    , false);
        }
        return config;
    }

    @Override
    public String getValueByKey(String key) {
        SysConfig config = getByKey(key);
        Assert.notNull(config, MsgUtil.t("data.not.exists", "配置" + key));
        return config.getValue();
    }
}