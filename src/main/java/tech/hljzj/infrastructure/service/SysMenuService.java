package tech.hljzj.infrastructure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuListVo;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单管理 sys_menu
 * 业务接口
 *
 * @author wa
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 实体创建
     *
     * @return 操作是否成功
     */
    boolean entityCreate(SysMenu entity);

    /**
     * 实体修改
     *
     * @return 操作是否成功
     */
    boolean entityUpdate(SysMenu entity);

    /**
     * 实体删除
     *
     * @return 操作是否成功
     */
    boolean entityDelete(SysMenu entity);

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
    SysMenu entityGet(SysMenu entity);

    /**
     * 实体获取
     *
     * @param id ID
     * @return 操作是否成功
     */
    SysMenu entityGet(Serializable id);

    /**
     * 查询数据
     *
     * @param query 检索条件
     * @return 查询结果
     */
    Page<SysMenu> page(SysMenuQueryVo query);

    /**
     * 查询数据列表
     *
     * @param query 检索条件
     * @return 查询结果
     */
    List<SysMenu> list(SysMenuQueryVo query);

    void entityUpdateSort(String rowId, String prevRowId, String nextRowId);

    @Transactional(rollbackFor = Exception.class)
    List<ExcelUtil.FailRowWrap<SysMenuListVo>> importData(String appId, MultipartFile file) throws IOException;
}