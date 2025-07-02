package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.domain.SysDeptRole;
import tech.hljzj.infrastructure.mapper.SysDeptRoleMapper;
import tech.hljzj.infrastructure.service.SysDeptRoleService;
import tech.hljzj.infrastructure.vo.SysDeptRole.SysDeptRoleQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 部门关联角色 sys_dept_role_ 
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDeptRoleServiceImpl extends ServiceImpl<SysDeptRoleMapper, SysDeptRole> implements SysDeptRoleService {

    @Override
    public SysDeptRole entityGet(SysDeptRole entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDeptRole entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysDeptRole entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysDeptRole entity) {
        SysDeptRole existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysDeptRole entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<SysDeptRole> page(SysDeptRoleQueryVo query) {
        Page<SysDeptRole> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysDeptRole> list(SysDeptRoleQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

}