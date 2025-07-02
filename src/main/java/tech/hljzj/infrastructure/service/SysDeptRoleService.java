package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDeptRole;
import tech.hljzj.infrastructure.vo.SysDeptRole.*;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

/**
 * 部门关联角色 sys_dept_role_ 
 * 业务接口
 *
 * @author wa
 */
public interface SysDeptRoleService  extends IService<SysDeptRole> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDeptRole entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDeptRole entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDeptRole entity);

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
    SysDeptRole entityGet(SysDeptRole entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDeptRole entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query      检索条件
     * @return 查询结果
     */
    Page<SysDeptRole> page(SysDeptRoleQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDeptRole> list(SysDeptRoleQueryVo query);
}