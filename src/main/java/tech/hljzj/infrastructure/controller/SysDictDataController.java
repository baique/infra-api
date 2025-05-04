package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
import tech.hljzj.infrastructure.service.SysDictDataService;
import tech.hljzj.infrastructure.vo.SysDictData.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 字典项 sys_dict_data_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_dict_data")
public class SysDictDataController extends BaseController {
    private final SysDictDataService service;
    public static final String MODULE_NAME = "字典项管理";

    @Autowired
    public SysDictDataController(SysDictDataService service) {
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
    public R<SysDictDataDetailVo> entityGet(@PathVariable Serializable id) {
        SysDictData dto = this.service.entityGet(id);
        return R.ok(new SysDictDataDetailVo().fromDto(dto));
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
    public R<SysDictDataDetailVo> entityCreate(@RequestBody @Validated SysDictDataNewVo entity) {
        SysDictData dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysDictDataDetailVo().fromDto(dto));
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
    public R<SysDictDataDetailVo> entityUpdate(@RequestBody @Validated SysDictDataUpdateVo entity) {
        SysDictData dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysDictDataDetailVo().fromDto(dto));
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
    public void export(@RequestBody SysDictDataQueryVo query, HttpServletResponse response) {
        List<SysDictData> listDto = this.service.list(query);

        List<SysDictDataListVo> listVo = new ArrayList<>();
        for (SysDictData item : listDto) {
            SysDictDataListVo vo = new SysDictDataListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysDictDataListVo.class);
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
    public R<List<ExcelUtil.FailRowWrap<SysDictDataListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(ExcelUtil.getType(file.getOriginalFilename()),file.getInputStream(), SysDictDataListVo.class, SysDictDataListVo -> {
            this.service.entityCreate(SysDictDataListVo.toDto());
        }));
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
    public R<R.PageResult<SysDictDataListVo>> page(@RequestBody SysDictDataQueryVo query) {
        Page<SysDictData> page = this.service.page(query);
        Page<SysDictDataListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysDictDataListVo> list = page.getRecords().stream().map((Function<SysDictData, SysDictDataListVo>) entity -> {
            SysDictDataListVo vo = new SysDictDataListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

}