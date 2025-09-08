package tech.hljzj.infrastructure.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.service.SysMenuService;
import tech.hljzj.infrastructure.vo.SysMenu.*;
import tech.hljzj.protect.anno.Xss;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 菜单管理 sys_menu
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_menu")
public class SysMenuController extends BaseController {
    public static final String MODULE_NAME = "菜单管理";
    private final SysMenuService service;

    public SysMenuController(SysMenuService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:menu:query','sys:menu:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    @Xss(enable = false)
    public R<SysMenuDetailVo> entityGet(@PathVariable Serializable id) {
        SysMenu dto = this.service.entityGet(id);
        return R.ok(new SysMenuDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:menu:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysMenuDetailVo> entityCreate(@RequestBody @Validated SysMenuNewVo entity) {
        SysMenu dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysMenuDetailVo().fromDto(dto));
    }

    /**
     * 新增菜单组
     *
     * @param key 菜单标识
     * @param name 菜单名称
     * @param ownerAppId 所属应用
     * @return 创建结果
     */
    @PreAuthorize("auth('sys:menu:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert-group")
    public R<Boolean> entityCreateGroup(String key, String name, String ownerAppId) {
        this.service.entityCreateGroup(key, name, ownerAppId);
        return R.ok(true);
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:menu:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysMenuDetailVo> entityUpdate(@RequestBody @Validated SysMenuUpdateVo entity) {
        SysMenu dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysMenuDetailVo().fromDto(dto));
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:menu:delete')")
    @Log(title = MODULE_NAME, operType = BusinessType.DELETE)
    public R<Boolean> entityDelete(@RequestBody @Validated List<Serializable> ids) {
        return R.ok(this.service.entityBatchDelete(ids));
    }


    /**
     * 导出数据
     *
     * @param query 数据查询
     * @return 导出结果
     */
    @PostMapping("/export")
    @PreAuthorize("auth('sys:menu:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysMenuQueryVo query, HttpServletResponse response) {
        List<SysMenu> listDto = this.service.list(query);

        List<SysMenuListVo> listVo = new ArrayList<>();
        for (SysMenu item : listDto) {
            SysMenuListVo vo = new SysMenuListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysMenuListVo.class);
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:menu:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    @Xss(enable = false)
    public R<List<ExcelUtil.FailRowWrap<SysMenuListVo>>> importData(String appId, @RequestPart(name = "files") MultipartFile file) throws IOException {
        List<ExcelUtil.FailRowWrap<SysMenuListVo>> d = service.importData(appId, file);
        if (CollUtil.isNotEmpty(d)) {
            throw UserException.defaultError("数据不符合要求，请处理后重新上传(此功能建议在专业人员指导下使用)");
        }
        return R.ok(d);
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
//    @PreAuthorize("auth('sys:menu:list')")
    @Xss(enable = false)
    public R<R.PageResult<SysMenuListVo>> page(@RequestBody SysMenuQueryVo query) {
        Page<SysMenu> page = this.service.page(query);
        Page<SysMenuListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysMenuListVo> list = page.getRecords().stream().map((Function<SysMenu, SysMenuListVo>) entity -> {
            SysMenuListVo vo = new SysMenuListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list/all")
//    @PreAuthorize("auth('sys:menu:list')")
    @Xss(enable = false)
    public R<?> listAll(@RequestBody SysMenuQueryVo query) {
        List<SysMenu> page = this.service.list(query);
        List<SysMenuListVo> list = page.stream().map((Function<SysMenu, SysMenuListVo>) entity -> {
            SysMenuListVo vo = new SysMenuListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        return R.ok(list);
    }


    /**
     * 修改数据
     *
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     * @return 修改后
     */
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE, functionName = "排序")
    @PostMapping("/sort")
    public R<SysDictData> applySort(String rowId, String prevRowId, String nextRowId) {
        this.service.entityUpdateSort(rowId, prevRowId, nextRowId);
        return R.ok();
    }

}