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
import tech.hljzj.framework.util.convert.Convert;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysDeptIdentity;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.service.SysDeptIdentityService;
import tech.hljzj.infrastructure.vo.SysDeptIdentity.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 岗位管理 sys_dept_identity_ 
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/identity")
public class SysDeptIdentityController extends BaseController {
    public static final String MODULE_NAME = "岗位管理";
    private final SysDeptIdentityService service;

    @Autowired
    public SysDeptIdentityController(SysDeptIdentityService service) {
        this.service = service;
    }

    /**
     * 以字典形式返回岗位信息
     * @return 字典形式岗位信息
     */
    @GetMapping("/options")
    public R<List<Convert.Options<String, String>>> options() {
        return R.ok(Convert.toOptions(service.list(), SysDeptIdentity::getName, SysDeptIdentity::getKey));
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:identity:query','sys:identity:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysDeptIdentityDetailVo> entityGet(@PathVariable Serializable id) {
        SysDeptIdentity dto = this.service.entityGet(id);
        return R.ok(new SysDeptIdentityDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:identity:add')")
    @PostMapping("/insert")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    public R<SysDeptIdentityDetailVo> entityCreate(@RequestBody @Validated SysDeptIdentityNewVo entity) {
        SysDeptIdentity dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysDeptIdentityDetailVo().fromDto(dto));
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:identity:edit')")
    @PostMapping("/update")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    public R<SysDeptIdentityDetailVo> entityUpdate(@RequestBody @Validated SysDeptIdentityUpdateVo entity) {
        SysDeptIdentity dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysDeptIdentityDetailVo().fromDto(dto));
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:identity:delete')")
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
    @PreAuthorize("auth('sys:identity:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysDeptIdentityQueryVo query, HttpServletResponse response) {
        List<SysDeptIdentity> listDto = this.service.list(query);

        List<SysDeptIdentityListVo> listVo = new ArrayList<>();
        for (SysDeptIdentity item : listDto) {
            SysDeptIdentityListVo vo = new SysDeptIdentityListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysDeptIdentityListVo.class);
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:identity:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<List<ExcelUtil.FailRowWrap<SysDeptIdentityListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(file.getInputStream(), SysDeptIdentityListVo.class, SysDeptIdentityListVo -> {
            this.service.entityCreate(SysDeptIdentityListVo.toDto());
        }));
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
    @PreAuthorize("auth('sys:identity:list')")
    public R<R.PageResult<SysDeptIdentityListVo>> page(@RequestBody SysDeptIdentityQueryVo query) {
        Page<SysDeptIdentity> page = this.service.page(query);
        Page<SysDeptIdentityListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysDeptIdentityListVo> list = page.getRecords().stream().map((Function<SysDeptIdentity, SysDeptIdentityListVo>) entity -> {
            SysDeptIdentityListVo vo = new SysDeptIdentityListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
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