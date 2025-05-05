package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysDictType;
import tech.hljzj.infrastructure.service.SysDictTypeService;
import tech.hljzj.infrastructure.vo.SysDictType.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 字典组 sys_dict_type_
 * 控制层
 *
 * @author
 */
@RestController
@RequestMapping("/infrastructure/sys_dict_type")
public class SysDictTypeController extends BaseController {
    private final SysDictTypeService service;
    public static final String MODULE_NAME = "字典组管理";

    public SysDictTypeController(SysDictTypeService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth('sys:dict:query')")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysDictTypeDetailVo> entityGet(@PathVariable Serializable id) {
        SysDictType dto = this.service.entityGet(id);
        return R.ok(new SysDictTypeDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:dict:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysDictTypeDetailVo> entityCreate(@RequestBody @Validated SysDictTypeNewVo entity) {
        SysDictType dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysDictTypeDetailVo().fromDto(dto));
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:dict:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysDictTypeDetailVo> entityUpdate(@RequestBody @Validated SysDictTypeUpdateVo entity) {
        SysDictType dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysDictTypeDetailVo().fromDto(dto));
    }

    /**
     * 排序
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

    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:dict:delete')")
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
    @PreAuthorize("auth('sys:dict:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysDictTypeQueryVo query, HttpServletResponse response) throws Exception {
        this.service.exportData(query, response.getOutputStream());
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:dict:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<Void> importData(@RequestPart(name = "files") MultipartFile file) throws Exception {
        this.service.importData(ExcelUtil.getType(file.getOriginalFilename()),file.getInputStream());
        return R.ok();
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
//    @PreAuthorize("auth('sys:dict:list')")
    @Anonymous
    public R<R.PageResult<SysDictTypeListVo>> page(@RequestBody SysDictTypeQueryVo query) {
        Page<SysDictType> page = this.service.page(query);
        Page<SysDictTypeListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysDictTypeListVo> list = page.getRecords().stream().map((Function<SysDictType, SysDictTypeListVo>) entity -> {
            SysDictTypeListVo vo = new SysDictTypeListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

}