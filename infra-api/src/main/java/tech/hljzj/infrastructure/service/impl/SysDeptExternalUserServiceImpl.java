package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.domain.SysDeptExternalUser;
import tech.hljzj.infrastructure.mapper.SysDeptExternalUserMapper;
import tech.hljzj.infrastructure.service.SysDeptExternalUserService;
import tech.hljzj.infrastructure.vo.SysDeptExternalUser.SysDeptExternalUserQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 外部编入人员 sys_dept_external_user_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDeptExternalUserServiceImpl extends ServiceImpl<SysDeptExternalUserMapper, SysDeptExternalUser> implements SysDeptExternalUserService {
    @Override
    public boolean exists(LambdaQueryWrapper<SysDeptExternalUser> condition) {
        return baseMapper.exists(condition);
    }

    @Override
    public SysDeptExternalUser entityGet(SysDeptExternalUser entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDeptExternalUser entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysDeptExternalUser entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysDeptExternalUser entity) {
        SysDeptExternalUser existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysDeptExternalUser entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<SysDeptExternalUser> page(SysDeptExternalUserQueryVo query) {
        Page<SysDeptExternalUser> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysDeptExternalUser> list(SysDeptExternalUserQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }


}