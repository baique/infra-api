package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysLog;
import tech.hljzj.infrastructure.service.SysLogService;
import tech.hljzj.infrastructure.vo.SysLog.SysLogDetailVo;
import tech.hljzj.infrastructure.vo.SysLog.SysLogListVo;
import tech.hljzj.infrastructure.vo.SysLog.SysLogNewVo;
import tech.hljzj.infrastructure.vo.SysLog.SysLogQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 操作日志 sys_log_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/sys_log_")
public class SysLogController extends BaseController {
    private final SysLogService service;
    public static final String MODULE_NAME = "操作日志";

    public SysLogController(SysLogService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:log:query','sys:log:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysLogDetailVo> entityGet(@PathVariable Serializable id) {
        SysLog dto = this.service.entityGet(id);
        return R.ok(new SysLogDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:log:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysLogDetailVo> entityCreate(@RequestBody @Validated SysLogNewVo entity) {
        SysLog dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysLogDetailVo().fromDto(dto));
    }


    /**
     * 导出数据
     *
     * @param query 数据查询
     * @return 导出结果
     */
    @PostMapping("/export")
    @PreAuthorize("auth('sys:log:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysLogQueryVo query, HttpServletResponse response) {
        List<SysLog> listDto = this.service.list(query);

        List<SysLogListVo> listVo = new ArrayList<>();
        for (SysLog item : listDto) {
            SysLogListVo vo = new SysLogListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysLogListVo.class);
    }

    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
    @PreAuthorize("auth('sys:log:list')")
    public R<R.PageResult<SysLogListVo>> page(@RequestBody SysLogQueryVo query) {
        Page<SysLog> page = this.service.page(query);
        Page<SysLogListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysLogListVo> list = page.getRecords().stream().map((Function<SysLog, SysLogListVo>) entity -> {
            SysLogListVo vo = new SysLogListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

}