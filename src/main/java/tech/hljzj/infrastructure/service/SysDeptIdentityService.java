package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDeptIdentity;
import tech.hljzj.infrastructure.vo.SysDeptIdentity.*;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

/**
 * 岗位管理 sys_dept_identity_ 
 * 业务接口
 *
 * @author wa
 */
public interface SysDeptIdentityService  extends IService<SysDeptIdentity> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDeptIdentity entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDeptIdentity entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDeptIdentity entity);

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
    SysDeptIdentity entityGet(SysDeptIdentity entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDeptIdentity entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query      检索条件
     * @return 查询结果
     */
    Page<SysDeptIdentity> page(SysDeptIdentityQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDeptIdentity> list(SysDeptIdentityQueryVo query);

    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);
}