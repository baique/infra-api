package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysConfig;
import tech.hljzj.infrastructure.vo.SysConfig.SysConfigQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统配置 sys_config
 * 业务接口
 *
 * @author wa
 */
public interface SysConfigService extends IService<SysConfig> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysConfig entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysConfig entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysConfig entity);

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
    SysConfig entityGet(SysConfig entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysConfig entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysConfig> page(SysConfigQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysConfig> list(SysConfigQueryVo query);

    /**
     * 应用排序
     *
     * @param id       标识
     * @param toPrevId 前一个元素的标识
     * @param toNextId 后一个元素的标识
     */
    void applySort(String id, String toPrevId, String toNextId);

    SysConfig getByKey(String key);

    /**
     * 获取配置，如果配置不存在，将抛出错误
     * @param key 配置标识
     * @return 配置值
     */
    String getValueByKey(String key);
}