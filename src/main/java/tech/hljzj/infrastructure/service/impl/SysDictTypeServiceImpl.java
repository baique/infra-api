package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.IDictService;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.service.entity.IDictData;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.framework.util.web.ReqUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysDictType;
import tech.hljzj.infrastructure.mapper.SysDictTypeMapper;
import tech.hljzj.infrastructure.service.SysDictDataService;
import tech.hljzj.infrastructure.service.SysDictTypeService;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysDictData.SysDictDataListVo;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeClone;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeListVo;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 字典类型 sys_dict_type
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService, IDictService {

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
            .eq(SysDictType::getOwnerAppId, entity.getOwnerAppId())
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
            .eq(SysDictType::getOwnerAppId, entity.getOwnerAppId())
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
        if (sysDictDataService.exists(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getOwnerTypeId, entity.getId()))) {
            // 如果包含了字段值，那么就不允许删除
            throw UserException.defaultError("当前字典组内仍有字典项，删除操作被拒绝");
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
    public Map<String, List<IDictData>> getDictData(List<String> keys) {
        if (CollUtil.isEmpty(keys)) {
            return Collections.emptyMap();
        }

        //此处需要判定，如果应用本身提供了字典，那么就使用应用的字典
        //反之，如果应用本身没有提供字典，那么就使用基座服务的字典
        String appId = AppScopeHolder.getScopeAppId();
        if (StrUtil.isBlank(appId)) {
            return Collections.emptyMap();
        }

        appId = StrUtil.blankToDefault(appId, AppConst.ID);

        List<SysDictType> dictTypeList = list(
            Wrappers.<SysDictType>lambdaQuery()
                .eq(SysDictType::getOwnerAppId, appId)
                .in(SysDictType::getKey, keys)
        );
        List<String> loadFromDefault = new ArrayList<>(keys);
        Map<String, String> keyMapping = new LinkedHashMap<>();
        dictTypeList.forEach(dictType -> {
            loadFromDefault.remove(dictType.getKey());
            keyMapping.put(dictType.getId(), dictType.getKey());
        });
        if (CollUtil.isNotEmpty(loadFromDefault)) {
            List<SysDictType> dictTypeListDef = list(
                Wrappers.<SysDictType>lambdaQuery()
                    .eq(SysDictType::getOwnerAppId, AppConst.ID)
                    .in(SysDictType::getKey, loadFromDefault)
            );

            loadFromDefault.clear();
            dictTypeListDef.forEach(dictType -> {
                keyMapping.put(dictType.getId(), dictType.getKey());
            });
        }


        if (CollUtil.isEmpty(keyMapping)) {
            return Collections.emptyMap();
        }

        Map<String, List<IDictData>> dd = new LinkedHashMap<>();
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneToApp(SysDictTypeClone cloneInfo) {
        SysDictType dictTypeInfo = getById(cloneInfo.getDictId());
        SysDictType newType = BeanUtil.copyProperties(dictTypeInfo, SysDictType.class, "id", "ownerAppId");
        newType.setOwnerAppId(cloneInfo.getCloneToAppId());

        boolean newEntity = entityCreate(newType);
        if (!newEntity) {
            throw UserException.defaultError("因为未知的原因导致了字典组创建失败");
        }

        List<SysDictData> dictDataSet = sysDictDataService.list(Wrappers.<SysDictData>lambdaQuery()
            .eq(SysDictData::getOwnerTypeId, cloneInfo.getDictId())
        );
        // 一般如果能创建类型，那么子项一定能克隆成功
        sysDictDataService.saveBatch(dictDataSet.stream().map(f -> BeanUtil.copyProperties(f, SysDictData.class, "id", "ownerAppId", "ownerTypeId"))
            .peek(f -> {
                f.setOwnerAppId(cloneInfo.getCloneToAppId());
                f.setOwnerTypeId(newType.getId());
            }).collect(Collectors.toSet()));
    }

    @Override
    public void exportData(SysDictTypeQueryVo query, OutputStream outputStream) throws Exception {
        List<String> loadDictData = new ArrayList<>();
        // 这里
        List<SysDictTypeListVo> list = this.list(query.buildQueryWrapper()).stream().map(f -> {
            SysDictTypeListVo d = new SysDictTypeListVo();
            loadDictData.add(f.getKey());
            d.fromDto(f);
            return d;
        }).collect(Collectors.toList());

        Map<String, List<IDictData>> dd = getDictData(loadDictData);


        Map<WriteSheet, List<?>> writeMap = new LinkedHashMap<>();
        writeMap.put(
            EasyExcel.writerSheet("字典组数据")
                .head(SysDictTypeListVo.class)
                .build()
            , (list)
        );

        for (SysDictTypeListVo d : list) {
            List<SysDictDataListVo> dictData = dd.get(d.getKey()).stream().map(f -> {
                SysDictDataListVo v = new SysDictDataListVo();
                v.fromDto((SysDictData) f);
                return v;
            }).collect(Collectors.toList());

            writeMap.put(EasyExcel.writerSheet(d.getName() + "数据")
                .head(SysDictDataListVo.class)
                .build(), dictData);
        }
        try (ExcelWriter writer = EasyExcel.write(outputStream).build()) {
            writeMap.forEach((s, v) -> writer.write(v, s));
        }
    }

    @Autowired
    private TransactionTemplate template;

    @Override
    public void importData(ExcelTypeEnum typeEnum, InputStream inputStream) throws Exception {
        try (ExcelReader reader = EasyExcel.read(inputStream).excelType(typeEnum).build()) {
            SysDictTypeService typeService = this;
            List<ReadSheet> afterReadSheets = new ArrayList<>();
            ReadSheet indexSheet = EasyExcel.readSheet("字典组数据")
                .head(SysDictTypeListVo.class)
                .registerReadListener(new ReadListener<SysDictTypeListVo>() {

                    @Override
                    public void invoke(SysDictTypeListVo typeDict, AnalysisContext context) {
                        Validator v = ExcelUtil.factory.getValidator();
                        v.validate(typeDict);
                        SysDictType typeDictDto = typeDict.toDto();
                        afterReadSheets.add(EasyExcel
                            .readSheet(typeDictDto.getName() + "数据")
                            .head(SysDictDataListVo.class)
                            .registerReadListener(new DictTypeWithDataImport(
                                typeDictDto,
                                typeService,
                                sysDictDataService,
                                template
                            ))
                            .build()
                        );
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                })
                .build();
            reader.read(indexSheet);
            reader.read(afterReadSheets);
        }
    }

    public static class DictTypeWithDataImport implements ReadListener<SysDictDataListVo> {
        private final List<SysDictData> dictDataList = new ArrayList<>();
        private final SysDictType typeDict;
        private final TransactionTemplate template;
        private final SysDictTypeService typeService;
        private final SysDictDataService dataService;

        public DictTypeWithDataImport(SysDictType typeDict, SysDictTypeService typeService, SysDictDataService dataService, TransactionTemplate template) {
            this.typeDict = typeDict;
            this.template = template;
            this.typeService = typeService;
            this.dataService = dataService;
        }


        @Override
        public void invoke(SysDictDataListVo valueData, AnalysisContext context) {
            Validator v = ExcelUtil.factory.getValidator();
            v.validate(valueData);
            dictDataList.add(valueData.toDto());
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            this.template.executeWithoutResult(transactionStatus -> {
                SysDictType existsType = typeService.getOne(
                    Wrappers.lambdaQuery(SysDictType.class)
                        .eq(SysDictType::getKey, typeDict.getKey()),
                    false
                );

                if (existsType != null) {
                    existsType.updateForm(typeDict);
                    typeService.updateById(existsType);
                } else {
                    existsType = typeDict;
                    typeService.save(existsType);
                }
                for (SysDictData sysDictData : dictDataList) {
                    SysDictData existsValueData = dataService.getOne(Wrappers.lambdaQuery(SysDictData.class)
                            .eq(SysDictData::getOwnerTypeId, existsType.getId())
                            .eq(SysDictData::getKey, sysDictData.getKey()),
                        false
                    );
                    if (existsValueData != null) {
                        existsValueData.updateForm(sysDictData);
                        dataService.updateById(existsValueData);
                    } else {
                        dataService.save(sysDictData);
                    }
                }
            });

        }
    }
}