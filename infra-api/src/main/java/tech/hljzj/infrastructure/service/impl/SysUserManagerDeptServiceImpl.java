package tech.hljzj.infrastructure.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.domain.SysUserManagerDept;
import tech.hljzj.infrastructure.mapper.SysUserManagerDeptMapper;
import tech.hljzj.infrastructure.service.SysUserManagerDeptService;

import java.io.Serializable;
import java.util.Collection;


/**
 * 用户管辖组织机构 sys_user_manager_dept_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysUserManagerDeptServiceImpl extends ServiceImpl<SysUserManagerDeptMapper, SysUserManagerDept> implements SysUserManagerDeptService {

    @Override
    public SysUserManagerDept entityGet(SysUserManagerDept entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysUserManagerDept entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysUserManagerDept entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysUserManagerDept entity) {
        SysUserManagerDept existData = getOne(Wrappers.
                        <SysUserManagerDept>lambdaQuery()
                        .eq(SysUserManagerDept::getDeptId, entity.getDeptId())
                        .eq(SysUserManagerDept::getUserId, entity.getUserId()),
                false
        );
        if (existData != null) {
            existData.updateForm(entity);
            return updateById(existData);
        }else{
            return save(entity);
        }
    }


    @Override
    public boolean entityDelete(SysUserManagerDept entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }

}