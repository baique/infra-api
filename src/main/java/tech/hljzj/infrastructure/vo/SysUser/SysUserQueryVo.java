package tech.hljzj.infrastructure.vo.SysUser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.util.sql.SqlParamHelper;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysRoleService;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.SysUser.base.SysUserQueryBaseVo;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 用户管理 sys_user_
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public abstract class SysUserQueryVo<V extends SysUser> extends SysUserQueryBaseVo<V> {
    /**
     * 归属于某个部门
     * <p>
     * 此条件检索时，也包含该部门的后代部门
     */
    private String belongDeptId;
    /**
     * 同时持有某些角色的用户
     */
    private List<String> hasRole;
    /**
     * 同时持有某些角色的用户，与hasRole二选一使用
     */
    private List<SysRoleQueryVo> hasRoleQuery;

    public Consumer<MPJLambdaWrapper<? extends V>> conditionBelongDeptId() {
        return query -> {
            belongDeptId = StrUtil.blankToDefault(belongDeptId, "0");
            if (Objects.nonNull(belongDeptId) && !Objects.equals("0", belongDeptId)) {
                List<SysDept> deptIds = SpringUtil.getBean(SysDeptService.class).descendantsAll(belongDeptId);
                if (CollUtil.isNotEmpty(deptIds)) {
                    query.in("dept_id_", deptIds.stream().map(SysDept::getId).collect(Collectors.toList()));
                } else {
                    // false condition
                    query.apply("1 = 2");
                }
            }
        };
    }

    public Consumer<MPJLambdaWrapper<? extends V>> conditionHasRole() {
        return query -> {
            List<SysRole> roleList;
            if (CollUtil.isNotEmpty(hasRoleQuery)) {
                roleList = hasRoleQuery.stream()
                    .flatMap(queryVo -> SpringUtil.getBean(SysRoleService.class).list(queryVo).stream())
                    .collect(Collectors.toList());
            } else if (CollUtil.isNotEmpty(hasRole)) {
                //如果角色默认授予，那么就不需要作为判断条件，直接认为用户有即可
                SysRoleQueryVo queryVo = new SysRoleQueryVo();
                queryVo.setIdIn(hasRole);
                queryVo.setDefaultGrantNot(AppConst.YES);
                roleList = SpringUtil.getBean(SysRoleService.class).list(queryVo);
            } else {
                return;
            }
            if (CollUtil.isNotEmpty(roleList)) {
                //这里只需要考虑未默认授予的角色
                hasRole = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
                SqlParamHelper.InPreparedStatementParam roleParam = SqlParamHelper.toInPs(p -> "{" + hasRole.indexOf(p) + "}", hasRole);
                query.and(q -> q.exists(
                    "select user_id_ from sys_user_role_ " +
                        "where user_id_ = t.id_ and role_id_ in (" + roleParam.paramPart + ")",
                    roleParam.paramValue
                ));
            }
        };
    }

    @Override
    public <R extends V> MPJLambdaWrapper<R> buildQueryWrapper() {
        MPJLambdaWrapper<R> sp = super.buildQueryWrapper();
        this.conditionHasRole().accept(sp);
        this.conditionBelongDeptId().accept(sp);
        return sp;
    }
}