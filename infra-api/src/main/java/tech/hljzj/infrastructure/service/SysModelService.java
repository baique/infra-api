package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * 功能扩展模型 sys_model_
 * 业务接口
 *
 * @author wa
 */
public interface SysModelService extends IService<SysModel> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysModel entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysModel entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysModel entity);

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
    SysModel entityGet(SysModel entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysModel entityGet(Serializable id);
}