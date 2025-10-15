package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.infrastructure.mapper.SysUserAllowIpMapper;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.infrastructure.service.SysUserAllowIpService;
import tech.hljzj.infrastructure.vo.SysUserAllowIp.*;
import java.lang.*;
import java.util.Date;
import java.io.Serializable;
import java.util.Collection;
  

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysUserAllowIpServiceImpl extends ServiceImpl<SysUserAllowIpMapper, SysUserAllowIp> implements SysUserAllowIpService {

    @Override
    public SysUserAllowIp entityGet(SysUserAllowIp entity) {
        return getOne(Wrappers.query(entity));
    }
    @Override
    public SysUserAllowIp entityGet(Serializable id) {
        return getById(id);
    }
  

    @Override
    public boolean entityCreate(SysUserAllowIp entity) {
        return save(entity);
    }
  

    @Override
    public boolean entityUpdate(SysUserAllowIp entity) {
        SysUserAllowIp existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);
        return updateById(existsEntity);
    }
  

    @Override
    public boolean entityDelete(SysUserAllowIp entity) {
        return removeById(entity);
    }
  

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }  
  

    @Override
    public Page<SysUserAllowIp> page(SysUserAllowIpQueryVo query) {
        Page<SysUserAllowIp> pageConfig = query.buildPagePlus();
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysUserAllowIp> list(SysUserAllowIpQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }
  
}