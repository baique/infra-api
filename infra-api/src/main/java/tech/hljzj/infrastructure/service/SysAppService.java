package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.vo.SysApp.SysAppExport;
import tech.hljzj.infrastructure.vo.SysApp.SysAppQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 应用管理 sys_app
 * 业务接口
 *
 * @author wa
 */
public interface SysAppService extends IService<SysApp> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysApp entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysApp entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysApp entity);

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
    SysApp entityGet(SysApp entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysApp entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query      检索条件
     * @return 查询结果
     */
    Page<SysApp> page(SysAppQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysApp> list(SysAppQueryVo query);

    SysAppExport exportAppData(String appId);

    void importData(SysAppExport data);
}