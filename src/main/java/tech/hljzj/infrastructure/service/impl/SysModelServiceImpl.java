package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.domain.SysModel;
import tech.hljzj.infrastructure.mapper.SysModelMapper;
import tech.hljzj.infrastructure.service.SysModelService;

import java.io.Serializable;
import java.util.Collection;


/**
 * 功能扩展模型 sys_model_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysModelServiceImpl extends ServiceImpl<SysModelMapper, SysModel> implements SysModelService {

    @Override
    public SysModel entityGet(SysModel entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysModel entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysModel entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysModel entity) {
        SysModel existsEntity = getById(entity.getModule());
        if (existsEntity == null) {
            existsEntity = entity;
            return save(existsEntity);
        } else {
            existsEntity.updateForm(entity);
            return updateById(existsEntity);
        }
    }


    @Override
    public boolean entityDelete(SysModel entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }

}