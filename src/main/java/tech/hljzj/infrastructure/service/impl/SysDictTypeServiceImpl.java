package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.IDictService;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.service.entity.DictData;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysDictType;
import tech.hljzj.infrastructure.mapper.SysDictTypeMapper;
import tech.hljzj.infrastructure.service.SysDictDataService;
import tech.hljzj.infrastructure.service.SysDictTypeService;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeQueryVo;

import java.io.Serializable;
import java.util.*;


/**
 * 字典类型 sys_dict_type
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService , IDictService {

    private final SysDictDataService sysDictDataService;
    private final SortService sortService;

    public SysDictTypeServiceImpl(SysDictDataService sysDictDataService, SortService sortService) {
        this.sysDictDataService = sysDictDataService;
        this.sortService = sortService;
    }

    @Override
    public SysDictType entityGet(SysDictType entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDictType entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysDictType entity) {
        if (baseMapper.exists(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getKey, entity.getKey())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "字典组标识"));
        }
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysDictType entity) {
        SysDictType existsEntity = getById(entity.getId());
        if (baseMapper.exists(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getKey, entity.getKey())
                .ne(SysDictType::getId, existsEntity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "字典组标识"));
        }
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityDelete(SysDictType entity) {
        // 同步删除所有的字典值
        sysDictDataService.remove(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getOwnerTypeId, entity.getId()));
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        listByIds(ids).forEach(this::entityDelete);
        return true;
    }


    @Override
    public Page<SysDictType> page(SysDictTypeQueryVo query) {
        Page<SysDictType> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }


    @Override
    public List<SysDictType> list(SysDictTypeQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityUpdateSort(String rowId, String prevRowId, String nextRowId) {
        return updateBatchById(sortService.applySort(
                rowId,
                prevRowId,
                nextRowId,
                baseMapper,
                (condition) -> condition.where()
                        .setEntityClass(SysDictType.class)
                        .orderByAsc(SysDictType::getSort)
                        .orderByDesc(SysDictType::getCreateTime)
                        .orderByDesc(SysDictType::getId)
        ));
    }


    @Override
    public Map<String, List<DictData>> getDictData(List<String> keys) {
        if (CollUtil.isEmpty(keys)) {
            return Collections.emptyMap();
        }
        List<SysDictType> dictTypeList = list(
                Wrappers.<SysDictType>lambdaQuery()
                        .in(SysDictType::getKey, keys)

        );
        Map<String, String> keyMapping = new LinkedHashMap<>();
        dictTypeList.forEach(dictType -> {
            keyMapping.put(dictType.getId(), dictType.getKey());
        });
        if (CollUtil.isEmpty(keyMapping)) {
            return Collections.emptyMap();
        }

        Map<String, List<DictData>> dd = new LinkedHashMap<>();
        sysDictDataService.list(Wrappers.
                <SysDictData>lambdaQuery()
                .in(SysDictData::getOwnerTypeId, keyMapping.keySet())
                .orderByAsc(SysDictData::getSort)
                .orderByDesc(SysDictData::getCreateTime)
                .orderByDesc(SysDictData::getId)
        ).forEach(v -> {
            String key = keyMapping.get(v.getOwnerTypeId());
            if (!dd.containsKey(key)) {
                dd.put(key, new ArrayList<>());
            }
            dd.get(key).add(v);
        });

        return dd;
    }
}