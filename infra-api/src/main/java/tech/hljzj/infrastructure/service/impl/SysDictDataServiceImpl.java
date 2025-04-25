package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.IDictService;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.mapper.SysDictDataMapper;
import tech.hljzj.infrastructure.service.SysDictDataService;
import tech.hljzj.infrastructure.vo.SysDictData.SysDictDataQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * 字典项 sys_dict_data_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Autowired
    private SortService sortService;

    @Override
    public SysDictData entityGet(SysDictData entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDictData entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysDictData entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysDictData entity) {
        SysDictData existsEntity = getById(entity.getId());
        // 如果数据依然是锁定的
        if (Objects.equals(AppConst.YES, existsEntity.getLocked()) && Objects.equals(existsEntity.getLocked(), entity.getLocked())) {
            throw UserException.defaultError(MsgUtil.t("data.locked", "字典数据"));
        }
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysDictData entity) {
        // 如果数据依然是锁定的
        if (Objects.equals(AppConst.YES, entity.getLocked())) {
            throw UserException.defaultError(MsgUtil.t("data.locked", "字典数据"));
        }
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return remove(Wrappers.<SysDictData>lambdaQuery()
                .in(SysDictData::getId, ids)
                .ne(SysDictData::getLocked, AppConst.YES)
        );
    }


    @Override
    public Page<SysDictData> page(SysDictDataQueryVo query) {
        Page<SysDictData> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysDictData> list(SysDictDataQueryVo query) {
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
                        .eq(SysDictData::getOwnerTypeId, condition.row().getOwnerTypeId())
                        .orderByAsc(SysDictData::getSort)
                        .orderByDesc(SysDictData::getCreateTime)
                        .orderByDesc(SysDictData::getId)
        ));
    }
}