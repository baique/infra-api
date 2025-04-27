package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.vo.SysDictData.SysDictDataQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 字典数据 sys_dict_data
 * 业务接口
 *
 * @author wa
 */
public interface SysDictDataService extends IService<SysDictData> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDictData entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDictData entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDictData entity);

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
    SysDictData entityGet(SysDictData entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDictData entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysDictData> page(SysDictDataQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDictData> list(SysDictDataQueryVo query);

    /**
     * 更新字典项排序
     * @param rowId 当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);
}