package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysLog;
import tech.hljzj.infrastructure.vo.SysLog.*;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

/**
 * 操作日志 sys_log_ 
 * 业务接口
 *
 * @author wa
 */
public interface SysLogService  extends IService<SysLog> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysLog entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysLog entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysLog entity);

    /**
     * 批量删除
     *
     * @param ids 目标实体ID
     * @return 操作是否成功
     */
    boolean entityBatchDelete(Collection<Serializable> ids);

    /**
     * 实体查询
     *
     * @return 操作是否成功
     */
    SysLog entityGet(SysLog entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysLog entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query      检索条件
     * @return 查询结果
     */
    Page<SysLog> page(SysLogQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysLog> list(SysLogQueryVo query);
}