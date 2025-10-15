package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.infrastructure.vo.SysUserAllowIp.*;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 业务接口
 *
 * @author wa
 */
public interface SysUserAllowIpService  extends IService<SysUserAllowIp> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysUserAllowIp entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysUserAllowIp entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysUserAllowIp entity);

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
    SysUserAllowIp entityGet(SysUserAllowIp entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysUserAllowIp entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query      检索条件
     * @return 查询结果
     */
    Page<SysUserAllowIp> page(SysUserAllowIpQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysUserAllowIp> list(SysUserAllowIpQueryVo query);
}