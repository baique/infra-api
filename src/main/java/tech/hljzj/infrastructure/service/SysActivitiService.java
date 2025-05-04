package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.activiti.config.Loader;
import tech.hljzj.infrastructure.domain.SysActiviti;
import tech.hljzj.infrastructure.vo.SysActiviti.*;

import java.lang.*;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

/**
 * 流程管理 sys_activiti_
 * 业务接口
 *
 * @author wa
 */
public interface SysActivitiService extends IService<SysActiviti> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysActiviti entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysActiviti entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysActiviti entity);

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
    SysActiviti entityGet(SysActiviti entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysActiviti entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysActiviti> page(SysActivitiQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysActiviti> list(SysActivitiQueryVo query);

    Loader getModelDetail(String id);

    void saveAndPublishModel(String id, Loader model);
}