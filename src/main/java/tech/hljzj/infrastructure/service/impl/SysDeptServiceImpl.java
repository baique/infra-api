package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.config.TreeCodeGenerate;
import tech.hljzj.infrastructure.domain.*;
import tech.hljzj.infrastructure.mapper.SysDeptMapper;
import tech.hljzj.infrastructure.mapper.VSysDeptMemberUserMapper;
import tech.hljzj.infrastructure.service.*;
import tech.hljzj.infrastructure.vo.SysApp.SysAppListVo;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptAncestors;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.SysDeptExternalUser.SysDeptExternalUserQueryVo;
import tech.hljzj.infrastructure.vo.SysRole.GrantAppRoleVo;
import tech.hljzj.infrastructure.vo.SysRole.SysGrantRoleListVo;
import tech.hljzj.infrastructure.vo.SysRole.SysLoginBindRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static tech.hljzj.infrastructure.code.AppConst.TREE_DELIMITER;


/**
 * 组织管理 sys_dept
 * 业务实现
 *
 * @author wa
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysUserService sysUserService;
    private final TreeCodeGenerate treeCodeGenerate;
    private final VSysDeptMemberUserMapper sysDeptMemberUserMapper;
    private final SysDeptExternalUserService sysDeptExternalUserService;
    private final SortService sortService;
    private final SysDeptRoleService sysDeptRoleService;
    private final SysRoleService sysRoleService;
    private final SysAppService sysAppService;


    @Override
    public SysDept entityGet(SysDept entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysDept entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityCreate(SysDept entity) {
        if (exists(Wrappers.lambdaQuery(SysDept.class)
            .eq(SysDept::getKey, entity.getKey())
            .eq(SysDept::getParentId, entity.getParentId())
        )) {
            throw UserException.defaultError("当前部门标识已被本层级内的其他部门使用，请修改后重新提交");
        }

        if (exists(Wrappers.lambdaQuery(SysDept.class)
            .eq(SysDept::getName, entity.getName())
            .eq(SysDept::getParentId, entity.getParentId())
        )) {
            throw UserException.defaultError("当前部门名称已被本层级内的其他部门使用，请修改后重新提交");
        }

        entity.setNodeKey(treeCodeGenerate.newDeptCode());
        String parentNodePath;
        if (StrUtil.equals("0", entity.getParentId())) {
            // 自身节点，假设此处，自身节点
            parentNodePath = TREE_DELIMITER + entity.getNodeKey();
        } else {
            SysDept parentNode = getById(entity.getParentId());
            parentNodePath = String.join(TREE_DELIMITER, parentNode.getNodePath(), entity.getNodeKey());
        }
        entity.setNodePath(parentNodePath);
        return save(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityUpdate(SysDept entity) {
        if (exists(Wrappers.lambdaQuery(SysDept.class)
            .eq(SysDept::getKey, entity.getKey())
            .eq(SysDept::getParentId, entity.getParentId())
            .ne(SysDept::getId, entity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "组织标识"));
        }

        if (exists(Wrappers.lambdaQuery(SysDept.class)
            .eq(SysDept::getName, entity.getName())
            .eq(SysDept::getParentId, entity.getParentId())
            .ne(SysDept::getId, entity.getId())
        )) {
            throw UserException.defaultError(MsgUtil.t("data.exists", "组织名称"));
        }

        SysDept existsEntity = getById(entity.getId());
        String prevNodeParent = existsEntity.getParentId();
        String newNodeParent = entity.getParentId();

        existsEntity.updateForm(entity);

        boolean parentNodeHasChange = !prevNodeParent.equals(newNodeParent);

        if (parentNodeHasChange) {
            String newParentNodePath;
            if (StrUtil.equals("0", entity.getParentId())) {
                newParentNodePath = "";
            } else {
                newParentNodePath = getById(entity.getParentId()).getNodePath();
            }
            String currentOldNodePath = existsEntity.getNodePath();
            String currentNewNodePath = String.join(TREE_DELIMITER, newParentNodePath, existsEntity.getNodeKey());
            existsEntity.setNodePath(currentNewNodePath);

            // 批量更新全部后代节点，指向新的前置节点
            baseMapper.updateNodePath(currentOldNodePath, currentNewNodePath);
        }


        return updateById(existsEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityDelete(SysDept entity) {
        //被删除的部门下不能下属机构
        if (exists(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, entity.getId()))) {
            throw UserException.defaultError("如要删除部门必须先移走当前部门内所有下属组织。");
        }


        // 被删除部门下中不能有兼职用户
        if (sysDeptExternalUserService.exists(Wrappers.<SysDeptExternalUser>
            lambdaQuery().eq(SysDeptExternalUser::getDeptId, entity.getId()))) {
            throw UserException.defaultError("如要删除部门必须先移走当前部门内所有成员。");
        }

        // 被删除的部门下不能有用户
        if (sysUserService.exists(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getDeptId, entity.getId()))) {
            throw UserException.defaultError("如要删除部门必须先移走当前部门内所有成员。");
        }

        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        listByIds(ids).forEach(this::entityDelete);
        return true;
    }


    @Override
    public Page<SysDept> page(SysDeptQueryVo query) {
        Page<SysDept> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.asc("sort_"));
        pageConfig.addOrder(OrderItem.desc("create_time_"));
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }


    @Override
    public List<SysDept> list(SysDeptQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

    @Override
    public Page<VSysDeptMemberUser> pageMember(VSysDeptMemberUserQueryVo query) {
        Page<VSysDeptMemberUser> page = query.buildPagePlus();
        MPJLambdaWrapper<VSysDeptMemberUser> queryWrapper = query.buildDeptMemberQueryWrapper();
        return sysDeptMemberUserMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean exists(Wrapper<SysDept> eq) {
        return baseMapper.exists(eq);
    }

    @Override
    public List<SysDept> descendantsAll(String deptId) {
        //查询所有后台节点
        SysDept deptInfo = getById(deptId);
        if (deptInfo == null) {
            return Collections.emptyList();
        }
        String nodePath = deptInfo.getNodePath();
        SysDeptQueryVo q = new SysDeptQueryVo();
        q.setNodePathPrefix(nodePath);
        return list(q);
    }

    @Override
    public SysDeptAncestors ancestorNodes(String deptId) {
        //查询所有后台节点
        SysDeptAncestors ancestors = new SysDeptAncestors();
        SysDept deptInfo = getById(deptId);
        if (deptInfo == null) {
            return ancestors;
        }
        ancestors.setCurrent(deptInfo);

        String nodePath = deptInfo.getNodePath();
        String[] parentNodeKeys = nodePath.split(TREE_DELIMITER);

        List<SysDept> ns = list(Wrappers.lambdaQuery(SysDept.class)
            .in(SysDept::getNodeKey, Arrays.asList(parentNodeKeys))
        );
        ancestors.setParent(ns.stream().filter(f -> f.getId().equals(deptInfo.getParentId())).findFirst().orElse(null));
        ancestors.setNodes(ns);
        return ancestors;
    }

    @Override
    public boolean grantRole(String roleId, List<String> deptIds) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            return false;
        }

        Map<String, List<SysDeptRole>> existValue = sysDeptRoleService.list(Wrappers.<SysDeptRole>lambdaQuery()
            .eq(SysDeptRole::getRoleId, roleId)
            .in(SysDeptRole::getDeptId, deptIds)
        ).stream().collect(Collectors.groupingBy(SysDeptRole::getDeptId));

        return sysDeptRoleService.saveBatch(deptIds.stream().filter(deptId -> !existValue.containsKey(deptId)).map(deptId -> {
            //这里执行插入
            SysDeptRole deptRole = new SysDeptRole();
            deptRole.setDeptId(deptId);
            deptRole.setAppId(role.getOwnerAppId());
            deptRole.setRoleId(roleId);
            deptRole.setScope(1);
            return deptRole;

        }).collect(Collectors.toSet()));
    }

    @Override
    public boolean grantRoles(String deptId, List<String> roleIds) {
        sysDeptRoleService.saveBatch(sysRoleService.listByIds(roleIds).stream().map(f -> {
            SysDeptRole deptRole = new SysDeptRole();
            deptRole.setDeptId(deptId);
            deptRole.setAppId(f.getOwnerAppId());
            deptRole.setScope(1);
            deptRole.setRoleId(f.getId());
            return deptRole;
        }).collect(Collectors.toSet()));
        return true;
    }

    @Override
    public boolean unGrantRole(String roleId, List<String> deptIds) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            return false;
        }
        return sysDeptRoleService.remove(Wrappers
            .<SysDeptRole>lambdaQuery()
            .eq(SysDeptRole::getRoleId, roleId)
            .in(SysDeptRole::getDeptId, deptIds)
        );
    }

    @Override
    public boolean unGrantRoles(String deptId, List<String> roleIds) {
        sysDeptRoleService.remove(Wrappers
            .<SysDeptRole>lambdaQuery()
            .in(SysDeptRole::getRoleId, roleIds)
            .eq(SysDeptRole::getDeptId, deptId)
        );
        return true;
    }

    @Override
    public Map<String, Boolean> roleAssignedToDepts(String roleId, List<String> deptIds) {

        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyMap();
        }
        List<SysDeptRole> sysDeptRoles = sysDeptRoleService.list(Wrappers
            .<SysDeptRole>lambdaQuery()
            .eq(SysDeptRole::getRoleId, roleId)
            .in(SysDeptRole::getDeptId, deptIds)
        );

        Map<String, Boolean> result = new HashMap<>();
        sysDeptRoles.forEach(f -> {
            String userId = f.getDeptId();
            deptIds.remove(userId);
            result.put(userId, true);
        });
        //剩余的userId就是没有此角色的用户
        deptIds.forEach(f -> {
            result.put(f, false);
        });

        return result;
    }

    @Override
    public List<GrantAppRoleVo> grantDeptAppWithRole(String deptId, String appId) {
        // 这将同时得到应用和角色标识
        List<SysDeptRole> grantUserRoleIds = sysDeptRoleService.list(Wrappers.<SysDeptRole>lambdaQuery()
            .eq(SysDeptRole::getDeptId, deptId)
            .eq(StrUtil.isNotBlank(appId), SysDeptRole::getAppId, appId)
        );
        Map<String, List<SysDeptRole>> appRoleGroup = grantUserRoleIds
            .stream().collect(Collectors.groupingBy(SysDeptRole::getAppId));

        if (CollUtil.isEmpty(appRoleGroup)) {
            return Collections.emptyList();
        }

        return sysAppService.listByIds(appRoleGroup.keySet()).stream().map(app -> {
                Map<String, SysDeptRole> userRoleMap = new HashMap<>();
                Set<String> roleIds = appRoleGroup.get(app.getId())
                    .stream()
                    .peek(f -> userRoleMap.put(f.getRoleId(), f))
                    .map(SysDeptRole::getRoleId)
                    .collect(Collectors.toSet());
                List<SysGrantRoleListVo> roleList = sysRoleService
                    .listByIds(roleIds).stream()
                    .map(f -> new SysGrantRoleListVo().<SysGrantRoleListVo>fromDto(f))
                    .peek(f -> {
                        SysDeptRole sysDeptRole = userRoleMap.get(f.getId());
                        f.setExpiredTime(sysDeptRole.getExpiredTime());
                        f.setScope(sysDeptRole.getScope());
                    })
                    .collect(Collectors.toList());
                GrantAppRoleVo grantAppRoleVo = new GrantAppRoleVo();
                grantAppRoleVo.setRoleList(roleList);
                grantAppRoleVo.setAppInfo(new SysAppListVo().fromDto(app));
                return grantAppRoleVo;
            }).sorted((app1, app2) -> CompareUtil.compare(app1.getAppInfo().getSort(), app2.getAppInfo().getSort()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean updateGrantRoleExpiredTime(String deptId, String roleId, Date expiredTime) {
        if (expiredTime != null) {
            return sysDeptRoleService.update(
                Wrappers.<SysDeptRole>lambdaUpdate()
                    .eq(SysDeptRole::getDeptId, deptId)
                    .eq(SysDeptRole::getRoleId, roleId)
                    .set(SysDeptRole::getExpiredTime, expiredTime)
            );
        } else {
            return sysDeptRoleService.update(
                Wrappers.<SysDeptRole>lambdaUpdate()
                    .eq(SysDeptRole::getDeptId, deptId)
                    .eq(SysDeptRole::getRoleId, roleId)
                    .setSql("expired_time_ = null")
            );
        }
    }

    @Override
    public boolean updateGrantRoleGrantScope(String deptId, String roleId, int scope) {
        return sysDeptRoleService.update(
            Wrappers.<SysDeptRole>lambdaUpdate()
                .eq(SysDeptRole::getDeptId, deptId)
                .eq(SysDeptRole::getRoleId, roleId)
                .set(SysDeptRole::getScope, scope)
        );
    }

    @Override
    public List<SysLoginBindRole> listGrantRoleOfDept(String appId, String deptId) {
        SysDeptAncestors deptAncestors = this.ancestorNodes(deptId);

        List<SysDept> deptList = deptAncestors.getNodes();
        if (CollUtil.isEmpty(deptList)) {
            return Collections.emptyList();
        }
        SysDept current = deptAncestors.getCurrent();

        Map<String, String> deptNameMapping = deptList.stream().collect(Collectors.toMap(SysDept::getId, SysDept::getName));
        Map<String, String> sourceMapping = new HashMap<>();
        // 获取当前时间，确认需要删减的角色
        Date loginDate = new Date();
        List<SysDeptRole> sysDeptRoles = sysDeptRoleService.list(Wrappers
                .<SysDeptRole>lambdaQuery()
                .in(SysDeptRole::getDeptId, deptList.stream().map(SysDept::getId).collect(Collectors.toSet()))
                .eq(SysDeptRole::getAppId, appId)
            ).stream()
            .filter(f -> {
                if (ObjUtil.isNull(f.getExpiredTime())) {
                    return true;
                }
                return f.getExpiredTime().after(loginDate);
            }).
            filter(f -> {
                if (f.getDeptId().equals(deptId)) {
                    return true;
                }
                if (AppConst.DeptGrantScope.CHILDREN == f.getScope()) {
                    return f.getDeptId().equals(current.getParentId());
                }
                //后代
                return true;
            })
            .peek(f -> {
                sourceMapping.put(f.getRoleId(), deptNameMapping.get(f.getDeptId()));
            })
            .toList();
        SysRoleQueryVo q = new SysRoleQueryVo();
        LambdaQueryWrapper<SysRole> queryWrapper = q.buildQueryWrapper();
        if (CollUtil.isEmpty(sysDeptRoles)) {
            return Collections.emptyList();
        }
        queryWrapper.in(SysRole::getId, sysDeptRoles.stream().map(SysDeptRole::getRoleId).collect(Collectors.toList()));
        queryWrapper.orderByAsc(SysRole::getSort);
        queryWrapper.orderByDesc(SysRole::getId);
        return sysRoleService.list(queryWrapper).stream().map(f -> {
            SysLoginBindRole r = new SysLoginBindRole().fromDto(f);
            // 设置来源
            r.setSource(sourceMapping.get(r.getId()));
            return r;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean followDeptOfUsers(String deptId, List<String> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return true;
        }
        return sysDeptExternalUserService.saveBatch(userIds.stream().map(userId -> {
            SysDeptExternalUser memberUser = new SysDeptExternalUser();
            memberUser.setDeptId(deptId);
            memberUser.setUserId(userId);
            return memberUser;
        }).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unFollowDeptOfUsers(String deptId, List<String> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return true;
        }
        return sysDeptExternalUserService.remove(
            Wrappers.<SysDeptExternalUser>lambdaQuery()
                .eq(SysDeptExternalUser::getDeptId, deptId)
                .in(SysDeptExternalUser::getUserId, userIds)
        );
    }

    @Override
    public List<String> validateUserFollowStatus(String deptId, List<String> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        // 用部门标识和idIn进行检索即可
        SysDeptExternalUserQueryVo memberUser = new SysDeptExternalUserQueryVo();
        memberUser.setDeptId(deptId);
        memberUser.setUserIdIn(userIds);

        return sysDeptExternalUserService.list(memberUser)
            .stream().map(SysDeptExternalUser::getUserId)
            .collect(Collectors.toList());
    }


    /**
     * @param rowId     当前行ID
     * @param prevRowId 目标位置上一行ID
     * @param nextRowId 目标位置下一行ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityUpdateSort(String rowId, String prevRowId, String nextRowId) {
        updateBatchById(sortService.applySort(
            rowId,
            prevRowId,
            nextRowId,
            baseMapper,
            (condition) -> condition.where()
                .eq(SysDept::getParentId, condition.row().getParentId())
                .orderByAsc(SysDept::getSort)
                .orderByDesc(SysDept::getCreateTime)
                .orderByDesc(SysDept::getId)
        ));
    }

}