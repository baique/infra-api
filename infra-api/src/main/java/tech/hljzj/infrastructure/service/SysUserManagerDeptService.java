package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysUserManagerDept;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用户管辖组织机构 sys_user_manager_dept_ 
 * 业务接口
 *
 * @author wa
 */
public interface SysUserManagerDeptService  extends IService<SysUserManagerDept> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysUserManagerDept entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysUserManagerDept entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysUserManagerDept entity);

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
    SysUserManagerDept entityGet(SysUserManagerDept entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysUserManagerDept entityGet(Serializable id);
}