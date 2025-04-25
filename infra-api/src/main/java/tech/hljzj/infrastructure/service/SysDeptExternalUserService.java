package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDeptExternalUser;
import tech.hljzj.infrastructure.vo.SysDeptExternalUser.SysDeptExternalUserQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 外部编入人员 sys_dept_external_user_
 * 业务接口
 *
 * @author wa
 */
public interface SysDeptExternalUserService extends IService<SysDeptExternalUser> {
    boolean exists(LambdaQueryWrapper<SysDeptExternalUser> condition);

    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDeptExternalUser entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDeptExternalUser entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDeptExternalUser entity);

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
    SysDeptExternalUser entityGet(SysDeptExternalUser entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDeptExternalUser entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysDeptExternalUser> page(SysDeptExternalUserQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDeptExternalUser> list(SysDeptExternalUserQueryVo query);


}