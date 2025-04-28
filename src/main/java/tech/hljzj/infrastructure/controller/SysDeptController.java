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
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.vo.SysDept.*;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 组织管理 sys_dept
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_dept")
public class SysDeptController extends BaseController {
    private final SysDeptService service;
    public static final String MODULE_NAME = "组织管理";

    @Autowired
    public SysDeptController(SysDeptService service) {
        this.service = service;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth('sys:dept:query')")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysDeptDetailVo> entityGet(@PathVariable Serializable id) {
        SysDept dto = this.service.entityGet(id);
        return R.ok(new SysDeptDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:dept:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    @PostMapping("/insert")
    public R<SysDeptDetailVo> entityCreate(@RequestBody @Validated SysDeptNewVo entity) {
        SysDept dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysDeptDetailVo().fromDto(dto));
    }


    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:dept:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysDeptDetailVo> entityUpdate(@RequestBody @Validated SysDeptUpdateVo entity) {
        SysDept dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysDeptDetailVo().fromDto(dto));
    }

    /**
     * 数据排序
     *
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     * @return 修改后
     */
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE, functionName = "排序")
    @PostMapping("/sort")
    public R<Void> applySort(String rowId, String prevRowId, String nextRowId) {
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
    @PreAuthorize("auth('sys:dept:delete')")
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
    @PreAuthorize("auth('sys:dept:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody SysDeptQueryVo query, HttpServletResponse response) {
        List<SysDept> listDto = this.service.list(query);

        List<SysDeptListVo> listVo = new ArrayList<>();
        for (SysDept item : listDto) {
            SysDeptListVo vo = new SysDeptListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysDeptListVo.class);
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:dept:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<List<ExcelUtil.FailRowWrap<SysDeptListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(file.getInputStream(), SysDeptListVo.class, SysDeptListVo -> {
            this.service.entityCreate(SysDeptListVo.toDto());
        }));
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
//    @PreAuthorize("auth('sys:dept:list')")
    public R<R.PageResult<SysDeptListVo>> page(@RequestBody SysDeptQueryVo query) {
        Page<SysDept> page = this.service.page(query);
        Page<SysDeptListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysDeptListVo> list = page.getRecords().stream().map((Function<SysDept, SysDeptListVo>) entity -> {
            SysDeptListVo vo = new SysDeptListVo();
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
//    @PreAuthorize("auth('sys:dept:list')")
    public R<List<SysDeptListVo>> listAll(@RequestBody SysDeptQueryVo query) {
        List<SysDept> page = this.service.list(query);

        List<SysDeptListVo> list = page.stream().map((Function<SysDept, SysDeptListVo>) entity -> {
            SysDeptListVo vo = new SysDeptListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        return R.ok(list);
    }

    @PostMapping("/member/list")
    @PreAuthorize("auth('sys:dept:member:list')")
    @Log(title = MODULE_NAME, content = "查看成员", operType = BusinessType.DETAIL, isSaveResponseData = false)
    public R<R.PageResult<SysDeptMemberListVo>> pageMemeber(@RequestBody VSysDeptMemberUserQueryVo query) {
        Page<VSysDeptMemberUser> page = this.service.pageMember(query);
        Page<SysDeptMemberListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysDeptMemberListVo> list = page.getRecords().stream().map((Function<VSysDeptMemberUser, SysDeptMemberListVo>) entity -> {
            SysDeptMemberListVo vo = new SysDeptMemberListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }


    /**
     * 添加成员
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 授权动作是否成功
     */
    @PostMapping("followDeptOfUsers")
    @Log(title = MODULE_NAME, content = "添加成员", operType = BusinessType.UPDATE)
    public R<Boolean> followDeptOfUsers(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.followDeptOfUsers(deptId, userIds));
    }

    /**
     * 移出成员
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 取消授权动作是否成功
     */
    @Log(title = MODULE_NAME, content = "移出成员", operType = BusinessType.UPDATE)
    @PostMapping("unFollowDeptOfUsers")
    public R<Boolean> unFollowDeptOfUsers(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.unFollowDeptOfUsers(deptId, userIds));
    }

    /**
     * 验证用户是否已经在指定的部门兼任某些职务
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 兼属于此部门的用户标识
     */
    @PostMapping("validateUserFollowStatus")
    public R<List<String>> validateUserFollowStatus(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.validateUserFollowStatus(deptId, userIds));
    }
}