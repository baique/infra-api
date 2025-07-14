package tech.hljzj.infrastructure.controller;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.SysUserManagerDept;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.SysUserExtAttrService;
import tech.hljzj.infrastructure.service.SysUserLockService;
import tech.hljzj.infrastructure.service.SysUserManagerDeptService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysUser.*;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;
import tech.hljzj.protect.password.PasswordNotSafeException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 用户管理 sys_user_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_user")
public class SysUserController extends BaseController {
    private final SysUserService service;
    private final SysUserManagerDeptService sysUserManagerDeptService;
    public static final String MODULE_NAME = "用户管理";
    private final SysUserExtAttrService sysUserExtAttrService;
    private final SysUserLockService sysUserLockService;

    public SysUserController(SysUserService service, SysUserManagerDeptService sysUserManagerDeptService, SysUserExtAttrService sysUserExtAttrService, SysUserLockService sysUserLockService) {
        this.service = service;
        this.sysUserManagerDeptService = sysUserManagerDeptService;
        this.sysUserExtAttrService = sysUserExtAttrService;
        this.sysUserLockService = sysUserLockService;
    }

    /**
     * 获取数据
     *
     * @param id 数据id
     * @param fetchExtAttr 是否同时加载扩展属性
     * @return 数据详情
     */
    @PreAuthorize("auth({'sys:user:query','sys:user:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysUserDetailVo> entityGet(@PathVariable @NotNull Serializable id, Boolean fetchExtAttr) {
        SysUser dto = this.service.entityGet(id, ObjUtil.defaultIfNull(fetchExtAttr, false));
        return R.ok(new SysUserDetailVo().fromDto(dto));
    }


    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    @PreAuthorize("auth('sys:user:add')")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT, ignoreParamValue = {
        "oldPassword",
        "newPassword",
    })
    @PostMapping("/insert")
    public R<SysUserDetailVo> entityCreate(@RequestBody @Validated SysUserNewVo entity) {
        SysUser dto = entity.toDto();
        this.service.entityCreate(dto, entity.getAttribution());
        return R.ok(new SysUserDetailVo().fromDto(dto));
    }

    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    @PreAuthorize("auth('sys:user:edit')")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<SysUserDetailVo> entityUpdate(@RequestBody @Validated SysUserUpdateVo entity) {
        SysUser dto = entity.toDto();
        this.service.entityUpdate(dto, entity.getAttribution());
        return R.ok(new SysUserDetailVo().fromDto(dto));
    }

    /**
     * 修改用户锁定状态
     *
     * @param userId      用户标识
     * @param status 锁定状态，1：锁定，0：解锁
     * @return 修改后
     */
    @PreAuthorize("auth('sys:user:edit')")
    @Log(title = MODULE_NAME, functionName = "修改状态", operType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public R<Void> updateUserStatus(@NotNull String userId, @NotNull String status) {
        this.service.update(Wrappers.<SysUser>lambdaUpdate()
            .eq(SysUser::getId, userId)
            .set(SysUser::getStatus, status)
        );
        return R.ok();
    }

    /**
     * 修改用户锁定状态
     *
     * @param userId      用户标识
     * @param accountLock 锁定状态，1：锁定，0：解锁
     * @return 修改后
     */
    @PreAuthorize("auth('sys:user:edit')")
    @Log(title = MODULE_NAME, functionName = "修改锁定状态", operType = BusinessType.UPDATE)
    @PostMapping("/updateLockStatus")
    public R<Void> updateLockStatus(@NotNull String userId, @NotNull String accountLock) {
        SysUser u = this.service.getById(userId);
        if (AppConst.NOT.equals(accountLock)) {
            sysUserLockService.unLock(u.getUsername());
        } else {
            sysUserLockService.lock(u.getUsername());
        }
        return R.ok();
    }


    /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize("auth('sys:user:delete')")
    @Log(title = MODULE_NAME, operType = BusinessType.DELETE)
    public R<Boolean> entityDelete(@RequestBody @Validated List<Serializable> ids) {
        return R.ok(this.service.entityBatchDelete(ids));
    }


    /**
     * 导出数据
     *
     * @param query 数据查询
     */
    @PostMapping("/export")
    @PreAuthorize("auth('sys:user:export')")
    @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
    public void export(@RequestBody VSysUserQueryVo query, HttpServletResponse response) {
        List<VSysUser> listDto = this.service.list(query);

        List<SysUserListVo> listVo = new ArrayList<>();
        for (SysUser item : listDto) {
            SysUserListVo vo = new SysUserListVo();
            listVo.add(vo.fromDto(item));
        }

        ExcelUtil.exportExcel(response, listVo, "数据", SysUserListVo.class);
    }


    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    @PreAuthorize("auth('sys:user:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<List<ExcelUtil.FailRowWrap<SysUserListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(ExcelUtil.getType(file.getOriginalFilename()), file.getInputStream(), SysUserListVo.class, SysUserListVo -> {
            this.service.entityCreate(SysUserListVo.toDto());
        }));
    }


    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
    public R<R.PageResult<SysUserListVo>> page(@RequestBody VSysUserQueryVo query) {
        Page<VSysUser> page = this.service.page(query);
        Page<SysUserListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysUserListVo> list = page.getRecords().stream().map((Function<SysUser, SysUserListVo>) entity -> {
            SysUserListVo vo = new SysUserListVo();
            vo.fromDto(entity);
            //验证当前用户是否被锁定
            vo.setAccountLock(sysUserLockService.isLocked(entity.getUsername()) ? AppConst.YES : AppConst.NOT);
            return vo;
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
    @PreAuthorize("auth('sys:user:list')")
    public R<?> listAll(@RequestBody VSysUserQueryVo query) {
        List<VSysUser> page = this.service.list(query);

        List<SysUserListVo> list = page.stream().map((Function<SysUser, SysUserListVo>) entity -> {
            SysUserListVo vo = new SysUserListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        return R.ok(list);
    }

    /**
     * 验证某个角色是否分配给了指定的用户
     *
     * @param roleId  角色标识
     * @param userIds 用户列表
     * @return 分配结果
     */
    @PostMapping("/isRoleAssignedToUsers")
    public R<Map<String, Boolean>> isRoleAssignedToUsers(String roleId, @RequestBody List<String> userIds) {
        return R.ok(service.roleAssignedToUsers(roleId, userIds));
    }


    /**
     * 获取用户可访问的角色
     *
     * @param userId 用户标识
     * @param appId  应用标识（可选，不传时获取所有应用下数据）
     * @return 信息
     */
    @PostMapping("/grantUserAppWithRole")
    @Log(title = MODULE_NAME, functionName = "查看用户可访问应用和角色", operType = BusinessType.DETAIL)
    public R<List<GrantAppRoleVo>> grantUserAppWithRole(@NotBlank String userId, String appId) {
        return R.ok(service.grantAppWithRole(userId, appId));
    }


    /**
     * 授权某个角色给用户
     *
     * @param roleId  角色标识
     * @param userId 用户
     * @param expiredTime 过期时间，null表示不限
     * @return 授权动作是否成功
     */
    @PostMapping("updateGrantRoleExpiredTime")
    @Log(title = MODULE_NAME, functionName = "设置已分配角色的过期时间", operType = BusinessType.UPDATE)
    public R<Boolean> updateGrantRoleExpiredTime(@NotBlank String userId,
                                                 @NotBlank String roleId,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date expiredTime) {
        return R.ok(service.updateGrantRoleExpiredTime(userId, roleId, expiredTime));
    }

    /**
     * 授权某个角色给用户
     *
     * @param roleId  角色标识
     * @param userIds 用户列表
     * @return 授权动作是否成功
     */
    @PostMapping("grantRoleToUsers")
    @Log(title = MODULE_NAME, functionName = "分配用户可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> grantRoleToUsers(String roleId, @RequestBody List<String> userIds) {
        return R.ok(service.grantRole(roleId, userIds));
    }

    /**
     * 取消授权某个角色给用户
     *
     * @param roleId  角色标识
     * @param userIds 用户列表
     * @return 取消授权动作是否成功
     */
    @PostMapping("revokeRoleFromUsers")
    @Log(title = MODULE_NAME, functionName = "移除用户可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> revokeRoleFromUsers(String roleId, @RequestBody List<String> userIds) {
        return R.ok(service.unGrantRole(roleId, userIds));
    }

    /**
     * 授权某些角色给用户
     *
     * @param userId  用户标识
     * @param roleIds 角色列表
     * @return 动作是否成功
     */
    @PostMapping("grantRolesToUser")
    @Log(title = MODULE_NAME, functionName = "分配用户可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> grantRolesToUser(String userId, @RequestBody List<String> roleIds) {
        return R.ok(service.grantRoles(userId, roleIds));
    }


    /**
     * 取消授权某些角色给用户
     *
     * @param userId  用户标识
     * @param roleIds 角色列表
     * @return 动作是否成功
     */
    @PostMapping("revokeRolesToUser")
    @Log(title = MODULE_NAME, functionName = "移除用户可用角色", operType = BusinessType.UPDATE)
    public R<Boolean> revokeRolesToUser(String userId, @RequestBody List<String> roleIds) {
        return R.ok(service.unGrantRoles(userId, roleIds));
    }


    /**
     * 获取用户当前所有有效会话
     *
     * @param userId 用户标识
     * @return 当前所有凭据
     */
    @GetMapping("online/token")
    public R<List<TokenInfoVo>> onlineTokens(String userId) {
        return R.ok(service.listOnlineTokens(userId));
    }

    /**
     * 强制结束会话
     *
     * @param token 会话标识
     * @return 登出操作结果
     */
    @PostMapping("online/mandatoryLogoutToken")
    @Log(title = MODULE_NAME, functionName = "强制用户会话下线", operType = BusinessType.UPDATE)
    public R<Void> mandatoryLogoutToken(String token) {
        service.mandatoryLogoutToken(token);
        return R.ok();
    }

    /**
     * 新增用户的管辖组织
     *
     * @param managerDept 配置信息
     */
    @PostMapping("manager/org/insert")
    @Log(title = MODULE_NAME, functionName = "新增管辖组织", operType = BusinessType.UPDATE)
    public R<Void> insertUserManageOrg(@RequestBody SysUserManagerDept managerDept) {
        sysUserManagerDeptService.entityUpdate(managerDept);
        return R.ok();
    }

    /**
     * 修改用户的管辖组织
     *
     * @param managerDept 配置信息
     */
    @PostMapping("manager/org/update")
    @Log(title = MODULE_NAME, functionName = "修改管辖配置", operType = BusinessType.UPDATE)
    public R<Void> updateUserManageOrg(@RequestBody SysUserManagerDept managerDept) {
        sysUserManagerDeptService.entityUpdate(managerDept);
        return R.ok();
    }

    /**
     * 移除用户的管辖信息
     *
     * @param userId 用户标识
     * @param deptId 部门标识
     */
    @PostMapping("manager/org/delete")
    @Log(title = MODULE_NAME, functionName = "移除管辖组织", operType = BusinessType.UPDATE)
    public R<Boolean> delUserManagerOrg(String userId, String deptId) {
        return R.ok(sysUserManagerDeptService.remove(Wrappers
            .<SysUserManagerDept>lambdaQuery()
            .eq(SysUserManagerDept::getUserId, userId)
            .eq(SysUserManagerDept::getDeptId, deptId)
        ));
    }

    /**
     * 获取用户的管辖组织列表
     *
     * @param userId 用户标识
     */
    @Log(title = MODULE_NAME, functionName = "查看用户管辖组织", operType = BusinessType.DETAIL)
    @PostMapping("manager/org/list")
    public R<List<SysUserManagerDept>> listUserManagerOrg(String userId) {
        if (StrUtil.isBlank(userId)) {
            return R.ok(Collections.emptyList());
        }
        // 检索用户管辖的单位
        return R.ok(sysUserManagerDeptService.list(Wrappers.<SysUserManagerDept>lambdaQuery()
            .eq(SysUserManagerDept::getUserId, userId)
        ));
    }

    /**
     * 重置密码
     *
     * @param userId 用户标识
     */
    @Log(title = MODULE_NAME, functionName = "重置密码", operType = BusinessType.UPDATE, isSaveRequestData = true, isSaveResponseData = true)
    @PostMapping("resetPassword")
    public R<SysUserListVo> resetPassword(String userId) {
        SysUser user = service.getById(userId);
        if (user == null) {
            throw UserException.defaultError("用户不存在");
        }

        service.resetPassword(userId);
        return R.ok(new SysUserListVo().fromDto(user));
    }

    /**
     * 修改密码（使用ID重置）
     *
     * @param userId      用户标识
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    @Log(title = MODULE_NAME, functionName = "修改密码", operType = BusinessType.UPDATE, ignoreParamValue = {
        "oldPassword",
        "newPassword",
    })
    @PostMapping("changePassword")
    public R<Void> changePassword(String userId, String oldPassword, String newPassword) throws PasswordNotSafeException {
        service.changePassword(userId, oldPassword, newPassword);
        return R.ok();
    }

    /**
     * 修改密码（使用账号重置）
     *
     * @param username    用户账号
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    @Log(title = MODULE_NAME, functionName = "修改密码", operType = BusinessType.UPDATE, ignoreParamValue = {
        "oldPassword",
        "newPassword",
    })
    @PostMapping("changePasswordByUsername")
    @Anonymous
    public R<Void> changePasswordByUsername(String username, String oldPassword, String newPassword) throws PasswordNotSafeException {
        service.changePasswordByUsername(username, oldPassword, newPassword);
        return R.ok();
    }

    /**
     * 调整用户排序
     *
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     * @return 修改后
     */
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE, functionName = "排序")
    @PostMapping("/sort")
    public R<SysDictData> applySort(String rowId, String prevRowId, String nextRowId) {
        this.service.updateSortData(rowId, prevRowId, nextRowId);
        return R.ok();
    }

}