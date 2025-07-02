package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.infrastructure.domain.SysDeptIdentity;
import tech.hljzj.infrastructure.mapper.SysDeptIdentityMapper;
import tech.hljzj.infrastructure.service.SysDeptIdentityService;
import tech.hljzj.infrastructure.vo.SysDeptIdentity.SysDeptIdentityQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 岗位管理 sys_dept_identity_ 
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDeptIdentityServiceImpl extends ServiceImpl<SysDeptIdentityMapper, SysDeptIdentity> implements SysDeptIdentityService {

    private final SortService sortService;

    public SysDeptIdentityServiceImpl(SortService sortService) {
        this.sortService = sortService;
    }

    @Override
    public SysDeptIdentity entityGet(SysDeptIdentity entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDeptIdentity entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysDeptIdentity entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysDeptIdentity entity) {
        SysDeptIdentity existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysDeptIdentity entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<SysDeptIdentity> page(SysDeptIdentityQueryVo query) {
        Page<SysDeptIdentity> pageConfig = query.buildPagePlus();
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysDeptIdentity> list(SysDeptIdentityQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityUpdateSort(String rowId, String prevRowId, String nextRowId) {
        updateBatchById(sortService.applySort(
            rowId,
            prevRowId,
            nextRowId,
            baseMapper,
            (condition) -> condition.where()
                .setEntityClass(SysDeptIdentity.class)
                .orderByAsc(SysDeptIdentity::getSort)
                .orderByDesc(SysDeptIdentity::getId)
        ));
    }
}