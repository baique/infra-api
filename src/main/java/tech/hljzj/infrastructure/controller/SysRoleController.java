package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.vo.SysRole.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 角色管理 sys_role
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_role")
public class SysRoleController extends BaseController {
    private final SysRoleService service;
    public static final String MODULE_NAME = "角色管理";

    public SysRoleController(SysRoleService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:role:query','sys:role:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysRoleDetailVo> entityGet(@PathVariable Serializable id) {
        SysRole dto = this.service.entityGet(id);
        return R.ok(new SysRoleDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:role:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysRoleDetailVo> entityCreate(@RequestBody @Validated SysRoleNewVo entity) {
        SysRole dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysRoleDetailVo().fromDto(dto));
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:role:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysRoleDetailVo> entityUpdate(@RequestBody @Validated SysRoleUpdateVo entity) {
        SysRole dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysRoleDetailVo().fromDto(dto));
    }

    /**
     * 修改数据
     *
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     * @return 修改后
     */
    @PreAuthorize("auth('sys:role:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/sort")
    public R<Void> entityUpdate(String rowId, String prevRowId, String nextRowId) {
        this.service.entityUpdateSort(rowId, prevRowId, nextRowId);
        return R.ok();
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:role:delete')")
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
    @PreAuthorize("auth('sys:role:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysRoleQueryVo query, HttpServletResponse response) {
        List<SysRole> listDto = this.service.list(query);

        List<SysRoleListVo> listVo = new ArrayList<>();
        for (SysRole item : listDto) {
            SysRoleListVo vo = new SysRoleListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysRoleListVo.class);
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:role:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<List<ExcelUtil.FailRowWrap<SysRoleListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(ExcelUtil.getType(file.getOriginalFilename()),file.getInputStream(), SysRoleListVo.class, SysRoleListVo -> {
            this.service.entityCreate(SysRoleListVo.toDto());
        }));
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
//    @PreAuthorize("auth('sys:role:list')")
    public R<R.PageResult<SysRoleListVo>> page(@RequestBody SysRoleQueryVo query) {
        Page<SysRole> page = this.service.page(query);
        Page<SysRoleListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysRoleListVo> list = page.getRecords().stream().map((Function<SysRole, SysRoleListVo>) entity -> {
            SysRoleListVo vo = new SysRoleListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }


    /**
     * 查询全部数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list/all")
//    @PreAuthorize("auth('sys:role:list')")
    public R<List<SysRoleListVo>> listAll(@RequestBody SysRoleQueryVo query) {
        List<SysRole> page = this.service.list(query);

        List<SysRoleListVo> list = page.stream().map((Function<SysRole, SysRoleListVo>) entity -> {
            SysRoleListVo vo = new SysRoleListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        return R.ok(list);
    }

    /**
     * 获取角色在某个应用下可访问的菜单
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     */
    @PostMapping("/access/menu/list")
    @PreAuthorize("auth('sys:role:grant')")
    @Log(title = MODULE_NAME, functionName = "查看角色可访问菜单", operType = BusinessType.UPDATE)
    public R<?> grantList(String roleId, String appId) {
        return R.ok(service.listGrantOfRole(roleId, appId));
    }

    /**
     * 分配角色可访问的菜单
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     * @param menuId 菜单标识
     */
    @PostMapping("/access/menu/grant")
    @PreAuthorize("auth('sys:role:grant')")
    @Validated
    @Log(title = MODULE_NAME, functionName = "分配角色可访问菜单", operType = BusinessType.UPDATE)
    public R<?> grant(@NotNull(message = "需提供角色标识") String roleId, @NotNull(message = "需提供应用标识") String appId, @RequestBody List<String> menuId) {
        service.grant(roleId, appId, menuId);
        return R.ok();
    }

    /**
     * 移除角色可访问的菜单
     *
     * @param roleId 角色标识
     * @param appId  应用标识
     * @param menuId 菜单标识
     */
    @PostMapping("/access/menu/un-grant")
    @PreAuthorize("auth('sys:role:grant')")
    @Validated
    @Log(title = MODULE_NAME, functionName = "移除角色可访问菜单", operType = BusinessType.UPDATE)
    public R<?> unGrant(@NotNull(message = "角色标识不允许为空") String roleId, @NotNull(message = "应用标识不允许为空") String appId, @RequestBody List<String> menuId) {
        service.unGrant(roleId, appId, menuId);
        return R.ok();
    }


}