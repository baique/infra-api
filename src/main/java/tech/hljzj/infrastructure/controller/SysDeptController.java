package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import tech.hljzj.infrastructure.service.SysDeptImportService;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.vo.SysDept.*;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private SysDeptImportService importService;

    public SysDeptController(SysDeptService service) {
        this.service = service;
    }


    /**
     * 获取部门可访问的所有角色
     *
     * @param deptId 部门标识
     * @param appId  应用标识（可选，仅获取特定应用下的数据）
     * @return 信息
     */
    @PostMapping("/grantDeptAppWithRole")
    @Log(title = MODULE_NAME, functionName = "查看部门可访问应用和角色", operType = BusinessType.DETAIL)
    public R<List<GrantAppRoleVo>> grantDeptAppWithRole(@NotBlank String deptId, String appId) {
        return R.ok(service.grantDeptAppWithRole(deptId, appId));
    }


    /**
     * 授权某个角色给部门
     *
     * @param roleId  角色标识
     * @param deptId 部门
     * @param expiredTime 过期时间，null表示不限
     * @return 授权动作是否成功
     */
    @PostMapping("updateGrantRoleExpiredTime")
    @Log(title = MODULE_NAME, functionName = "设置已分配角色的过期时间", operType = BusinessType.UPDATE)
    public R<Boolean> updateGrantRoleExpiredTime(@NotBlank String deptId,
                                                 @NotBlank String roleId,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expiredTime) {
        return R.ok(service.updateGrantRoleExpiredTime(deptId, roleId, expiredTime));
    }


    /**
     * 授权某个角色给部门
     *
     * @param roleId  角色标识
     * @param deptId 部门
     * @param scope 授权范围
     *              1：仅本级
     *              2：仅子级
     *              3：所有后代共享
     * @return 授权动作是否成功
     */
    @PostMapping("updateGrantRoleScope")
    @Log(title = MODULE_NAME, functionName = "设置已分配角色的授权范围", operType = BusinessType.UPDATE)
    public R<Boolean> updateGrantRoleScope(@NotBlank String deptId,
                                           @NotBlank String roleId,
                                           int scope) {
        return R.ok(service.updateGrantRoleGrantScope(deptId, roleId, scope));
    }

    /**
     * 授权某个角色给部门
     *
     * @param roleId  角色标识
     * @param deptIds 部门列表
     * @return 授权动作是否成功
     */
    @PostMapping("grantRoleToDepts")
    @Log(title = MODULE_NAME, functionName = "分配部门可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> grantRoleToDepts(String roleId, @RequestBody List<String> deptIds) {
        return R.ok(service.grantRole(roleId, deptIds));
    }

    /**
     * 取消授权某个角色给部门
     *
     * @param roleId  角色标识
     * @param deptIds 部门列表
     * @return 取消授权动作是否成功
     */
    @PostMapping("revokeRoleFromDepts")
    @Log(title = MODULE_NAME, functionName = "移除部门可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> revokeRoleFromDepts(String roleId, @RequestBody List<String> deptIds) {
        return R.ok(service.unGrantRole(roleId, deptIds));
    }


    /**
     * 授权某些角色给部门
     *
     * @param deptId  部门标识
     * @param roleIds 角色列表
     * @return 动作是否成功
     */
    @PostMapping("grantRolesToDept")
    @Log(title = MODULE_NAME, functionName = "分配部门可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> grantRolesToDept(String deptId, @RequestBody List<String> roleIds) {
        return R.ok(service.grantRoles(deptId, roleIds));
    }


    /**
     * 取消授权某个角色给部门
     *
     * @param deptId  部门标识
     * @param roleIds 角色列表
     * @return 动作是否成功
     */
    @PostMapping("revokeRolesToDept")
    @Log(title = MODULE_NAME, functionName = "移除部门可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> revokeRolesToDept(String deptId, @RequestBody List<String> roleIds) {
        return R.ok(service.unGrantRoles(deptId, roleIds));
    }


    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:dept:query','sys:dept:edit'})")
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
     * 修改部门排序
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
    public R<Void> importData(@RequestPart MultipartFile file) throws IOException {
        importService.read(ExcelUtil.getType(file.getOriginalFilename()), file.getInputStream());
        return R.ok();
    }

    /**
     * 下载模板
     *
     */
    @PostMapping("/downloadTemplate")
    @PreAuthorize("auth('sys:dept:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public void downloadTemplate() {
        ExcelUtil.exportExcel(response, Collections.emptyList(), "数据", SysDeptImportVo.class);
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
     * 查询数据全部数据
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


    /**
     * 获取某个部门所有后代部门
     * @param deptId 部门标识
     * @return 后代部门
     */
    @PostMapping("/descendants")
    public R<List<SysDeptListVo>> descendants(String deptId) {
        return R.ok(
            service.descendantsAll(deptId)
                .stream()
                .map(f -> new SysDeptListVo().<SysDeptListVo>fromDto(f))
                .collect(Collectors.toList())
        );
    }

    /**
     * 获取某个部门所有先祖部门
     * @param deptId 部门标识
     * @return 后代部门
     */
    @PostMapping("/ancestors")
    public R<SysDeptAncestors.Vo> ancestors(String deptId) {
        SysDeptAncestors deptAncestors = service.ancestorNodes(deptId);
        return R.ok(deptAncestors.toVo());
    }

    /**
     * 通过部门编码获取某个部门所有后代部门
     * @param deptKey 部门编码
     * @return 后代部门
     */
    @PostMapping("/descendantsByKey")
    public R<List<SysDeptListVo>> descendantsByKey(String deptKey) {
        SysDept dept = service.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getKey, deptKey));
        return R.ok(
            service.descendantsAll(dept.getId())
                .stream()
                .map(f -> new SysDeptListVo().<SysDeptListVo>fromDto(f))
                .collect(Collectors.toList())
        );
    }

    /**
     * 通过部门编码获取某个部门所有先祖部门
     * @param deptKey 部门编码
     * @return 后代部门
     */
    @PostMapping("/ancestorsByKey")
    public R<SysDeptAncestors.Vo> ancestorsByKey(String deptKey) {
        SysDept dept = service.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getKey, deptKey));
        SysDeptAncestors deptAncestors = service.ancestorNodes(dept.getId());
        return R.ok(deptAncestors.toVo());
    }

    /**
     * 获取部门内成员信息
     * @param query 查询条件
     * @return 成员列表
     */
    @PostMapping("/member/list")
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
     * 添加兼属成员
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 授权动作是否成功
     */
    @PostMapping("followDeptOfUsers")
    @Log(title = MODULE_NAME, content = "添加成员", operType = BusinessType.UPDATE)
    @PreAuthorize("auth('sys:dept:member')")
    public R<Boolean> followDeptOfUsers(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.followDeptOfUsers(deptId, userIds));
    }

    /**
     * 移出兼属成员
     *
     * @param deptId  部门标识
     * @param userIds 用户
     * @return 取消授权动作是否成功
     */
    @Log(title = MODULE_NAME, content = "移出成员", operType = BusinessType.UPDATE)
    @PostMapping("unFollowDeptOfUsers")
    @PreAuthorize("auth('sys:dept:member')")
    public R<Boolean> unFollowDeptOfUsers(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.unFollowDeptOfUsers(deptId, userIds));
    }

    /**
     * 验证用户是否已经在指定的部门（兼属或主属）
     *
     * @param deptId  部门标识
     * @param userIds 用户列表
     * @return 兼属于此部门的用户标识
     */
    @PostMapping("validateUserFollowStatus")
    public R<List<String>> validateUserFollowStatus(String deptId, @RequestBody List<String> userIds) {
        return R.ok(service.validateUserFollowStatus(deptId, userIds));
    }
}