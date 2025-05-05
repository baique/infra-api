package tech.hljzj.infrastructure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.infrastructure.domain.SysModel;
import tech.hljzj.infrastructure.service.SysModelService;

import java.io.Serializable;


/**
 * 功能扩展模型 sys_model_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_model")
public class SysModelController extends BaseController {
    private final SysModelService service;

    public SysModelController(SysModelService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth('sys_model_:query')")
    @GetMapping("/{id}")
    public R<SysModel> entityGet(@PathVariable Serializable id) {
        return R.ok(this.service.entityGet(id));
    }


    /**
     * 修改数据
     *
     * @param dto 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys_model_:edit')")
    @Log(title = "功能扩展模型", operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysModel> entityUpdate(@RequestBody @Validated SysModel dto) {
        this.service.entityUpdate(dto);
        return R.ok(dto);
    }
}