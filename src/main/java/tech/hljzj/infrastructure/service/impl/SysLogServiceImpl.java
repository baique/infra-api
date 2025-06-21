package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.logger.LogEvent;
import tech.hljzj.framework.logger.SysLogEntity;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.domain.SysLog;
import tech.hljzj.infrastructure.mapper.SysLogMapper;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.service.SysLogService;
import tech.hljzj.infrastructure.util.AppScopeHolder;
import tech.hljzj.infrastructure.vo.SysLog.SysLogQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 操作日志 sys_log_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final AsyncTaskExecutor asyncTaskExecutor;
    private final SysAppService sysAppService;

    public SysLogServiceImpl(AsyncTaskExecutor asyncTaskExecutor, SysAppService sysAppService) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.sysAppService = sysAppService;
    }

    @Override
    public SysLog entityGet(SysLog entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysLog entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    public boolean entityCreate(SysLog entity) {
        return save(entity);
    }


    @Override
    public boolean entityUpdate(SysLog entity) {
        SysLog existsEntity = getById(entity.getId());

        return updateById(existsEntity);
    }


    @Override
    public boolean entityDelete(SysLog entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<SysLog> page(SysLogQueryVo query) {
        Page<SysLog> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.desc("op_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysLog> list(SysLogQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

    @EventListener(LogEvent.class)
    public void recordLog(LogEvent logEvent) {
        SysLogEntity entity = logEvent.getEntity();
        SysLog log = new SysLog();
        log.setOpAppId(AppScopeHolder.getScopeAppId());
        BeanUtil.copyProperties(entity, log);
        //使用一个异步线程完成
        asyncTaskExecutor.submit(() -> {
            if (StrUtil.isNotBlank(log.getOpAppId())) {
                // 这里记录是哪个应用上报的日志
                SysApp app = sysAppService.entityGet(log.getOpAppId());
                log.setAppName(app.getName());
            }
            baseMapper.insert(log);
        });
    }
}