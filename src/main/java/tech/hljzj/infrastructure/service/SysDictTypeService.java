package tech.hljzj.infrastructure.service;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDictType;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeClone;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeQueryVo;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 字典类型 sys_dict_type
 * 业务接口
 *
 * @author wa
 */
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDictType entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDictType entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDictType entity);

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
    SysDictType entityGet(SysDictType entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDictType entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysDictType> page(SysDictTypeQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDictType> list(SysDictTypeQueryVo query);

    boolean entityUpdateSort(String rowId, String prevRowId, String nextRowId);

    void cloneToApp(SysDictTypeClone cloneInfo);

    void exportData(SysDictTypeQueryVo query, OutputStream outputStream) throws Exception;

    void importData(ExcelTypeEnum typeEnum, InputStream inputStream) throws Exception;

}