package tech.hljzj.infrastructure.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.config.SwapEncoder;
import tech.hljzj.infrastructure.domain.SysApp;
import tech.hljzj.infrastructure.service.SysAppService;
import tech.hljzj.infrastructure.vo.SysApp.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 应用管理 sys_app
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_app")
public class SysAppController extends BaseController {
    public static final String MODULE_NAME = "应用管理";
    private final SysAppService service;
    private final SwapEncoder swapEncoder;
    private final SysAppService sysAppService;

    @Autowired
    public SysAppController(SysAppService service, SwapEncoder swapEncoder, SysAppService sysAppService) {
        this.service = service;
        this.swapEncoder = swapEncoder;
        this.sysAppService = sysAppService;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth('sys:app:query')")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysApp> entityGet(@PathVariable Serializable id) {
        return R.ok(this.service.entityGet(id));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:app:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysApp> entityCreate(@RequestBody @Validated SysAppNewVo entity) {
        SysApp dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(dto);
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:app:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysApp> entityUpdate(@RequestBody @Validated SysAppUpdateVo entity) {
        SysApp dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(dto);
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:app:delete')")
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
    @PreAuthorize("auth('sys:app:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysAppQueryVo query, HttpServletResponse response) {
        List<SysApp> listDto = this.service.list(query);

        List<SysAppListVo> listVo = new ArrayList<>();
        for (SysApp item : listDto) {
            SysAppListVo vo = new SysAppListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysAppListVo.class);
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
//    @PreAuthorize("auth('sys:app:list','sys:role:list','sys:menu:list')")
    public R<R.PageResult<SysAppListVo>> page(@RequestBody SysAppQueryVo query) {
        Page<SysApp> page = this.service.page(query);
        Page<SysAppListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysAppListVo> list = page.getRecords().stream().map((Function<SysApp, SysAppListVo>) entity -> {
            SysAppListVo vo = new SysAppListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

    /**
     * 导出数据
     *
     * @param appId 应用标识
     */
    @PostMapping("/exportData")
    @PreAuthorize("auth('sys:app:exportData')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void exportData(String appId, HttpServletResponse response) {
        SysAppExport data = this.service.exportAppData(appId);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String d = swapEncoder.encode(JSONUtil.toJsonStr(data));
            setDownloadFileName("应用数据(" + appId + ").enc");
            outputStream.write(d.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw UserException.defaultError("数据导出失败", e);
        }
    }


    /**
     * 导入数据
     */
    @PostMapping("/importData")
    @PreAuthorize("auth('sys:app:importData')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public R<Void> importData(@RequestPart(name = "files") MultipartFile file) throws IOException {
        String fileContent = new String(file.getBytes());
        Assert.notNull(fileContent, "文件格式错误:当前文件内不包含任何数据");
        try {
            String data = swapEncoder.decode(fileContent);
            SysAppExport d = JSONUtil.toBean(data, SysAppExport.class);
            Assert.notNull(d.getSysApp(), "应用数据解析失败:应用数据丢失");
            Assert.notNull(d.getConfigList(), "应用数据解析失败:配置数据丢失");
            Assert.notNull(d.getRoleList(), "应用数据解析失败:角色数据丢失");
            Assert.notNull(d.getMenuList(), "应用数据解析失败:菜单数据丢失");
            Assert.notNull(d.getRoleMenuGrantList(), "应用数据解析失败:角色菜单授权数据丢失");
            sysAppService.importData(d);
            return R.ok();
        } catch (Exception e) {
            throw UserException.defaultError("文件内容解析失败", e);
        }

    }

}