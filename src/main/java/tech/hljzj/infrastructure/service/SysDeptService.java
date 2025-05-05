package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.repository.IRepository;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 组织管理 sys_dept
 * 业务接口
 *
 * @author wa
 */
public interface SysDeptService extends IRepository<SysDept> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysDept entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysDept entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysDept entity);

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
    SysDept entityGet(SysDept entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysDept entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysDept> page(SysDeptQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysDept> list(SysDeptQueryVo query);

    /**
     * 获取某个部门的所有后代
     */
    List<SysDept> descendantsAll(String belongDeptId);

    boolean exists(Wrapper<SysDept> eq);

    Page<VSysDeptMemberUser> pageMember(VSysDeptMemberUserQueryVo query);

    Boolean followDeptOfUsers(String deptId, List<String> userIds);

    Boolean unFollowDeptOfUsers(String deptId, List<String> userIds);

    /**
     * 返回已经进行职务兼任的用户
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 确实有兼任某些职务的用户
     */
    List<String> validateUserFollowStatus(String deptId, List<String> userIds);


    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);
}