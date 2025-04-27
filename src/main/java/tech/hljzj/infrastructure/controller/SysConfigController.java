package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysConfig;
import tech.hljzj.infrastructure.service.SysConfigService;
import tech.hljzj.infrastructure.vo.SysConfig.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 系统配置 sys_config_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_config")
public class SysConfigController extends BaseController {
    public static final String MODULE_NAME = "系统配置";
    private final SysConfigService service;

    @Autowired
    public SysConfigController(SysConfigService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth('sys:config:query')")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysConfigDetailVo> entityGet(@PathVariable Serializable id) {
        SysConfig dto = this.service.entityGet(id);
        return R.ok(new SysConfigDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:config:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysConfigDetailVo> entityCreate(@RequestBody @Validated SysConfigNewVo entity) {
        SysConfig dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysConfigDetailVo().fromDto(dto));
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:config:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysConfigDetailVo> entityUpdate(@RequestBody @Validated SysConfigUpdateVo entity) {
        SysConfig dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysConfigDetailVo().fromDto(dto));
    }


    /**
     * 应用排序
     *
     * @param id       标识
     * @param toPrevId 前一个元素的标识
     * @param toNextId 后一个元素的标识
     * @return 操作结果
     */

    @PreAuthorize("auth('sys:config:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE, functionName = "排序")
    @PostMapping("/sort")
    public R<Void> applySort(String id, String toPrevId, String toNextId) {
        this.service.applySort(id, toPrevId, toNextId);
        return R.ok();
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:config:delete')")
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
    @PreAuthorize("auth('sys:config:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysConfigQueryVo query, HttpServletResponse response) {
        List<SysConfig> listDto = this.service.list(query);

        List<SysConfigListVo> listVo = new ArrayList<>();
        for (SysConfig item : listDto) {
            SysConfigListVo vo = new SysConfigListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysConfigListVo.class);
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
    @PreAuthorize("auth('sys:config:list')")
    public R<R.PageResult<SysConfigListVo>> page(@RequestBody SysConfigQueryVo query) {
        Page<SysConfig> page = this.service.page(query);
        Page<SysConfigListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysConfigListVo> list = page.getRecords().stream().map((Function<SysConfig, SysConfigListVo>) entity -> {
            SysConfigListVo vo = new SysConfigListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

}