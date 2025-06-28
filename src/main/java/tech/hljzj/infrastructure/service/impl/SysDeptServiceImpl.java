package tech.hljzj.infrastructure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.service.SortService;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.infrastructure.config.TreeCodeGenerate;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.SysDeptExternalUser;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.mapper.SysDeptMapper;
import tech.hljzj.infrastructure.mapper.VSysDeptMemberUserMapper;
import tech.hljzj.infrastructure.service.SysDeptExternalUserService;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.SysDeptExternalUser.SysDeptExternalUserQueryVo;
import tech.hljzj.infrastructure.vo.VSysDeptMemberUser.VSysDeptMemberUserQueryVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static tech.hljzj.infrastructure.code.AppConst.TREE_DELIMITER;


/**
 * 组织管理 sys_dept
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {


    private final SysUserService sysUserService;
    private final TreeCodeGenerate treeCodeGenerate;
    private final VSysDeptMemberUserMapper sysDeptMemberUserMapper;
    private final SysDeptExternalUserService sysDeptExternalUserService;
    private final SortService sortService;

    public SysDeptServiceImpl(SysUserService sysUserService, TreeCodeGenerate treeCodeGenerate, VSysDeptMemberUserMapper sysDeptMemberUserMapper, SysDeptExternalUserService sysDeptExternalUserService, SortService sortService) {
        this.sysUserService = sysUserService;
        this.treeCodeGenerate = treeCodeGenerate;
        this.sysDeptMemberUserMapper = sysDeptMemberUserMapper;
        this.sysDeptExternalUserService = sysDeptExternalUserService;
        this.sortService = sortService;
    }

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
            parentNodePath = String.join(TREE_DELIMITER, entity.getNodeKey());
        } else {
            SysDept parentNode = getById(entity.getParentId());
            parentNodePath = String.join(TREE_DELIMITER, parentNode.getNodePath(), parentNode.getNodeKey());
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
                newParentNodePath = String.join(TREE_DELIMITER, existsEntity.getNodeKey());
            } else {
                newParentNodePath = getById(entity.getParentId()).getNodePath();
            }
            String currentOldNodePath = String.join(TREE_DELIMITER, existsEntity.getNodePath());
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
    public List<SysDept> descendantsAll(String belongDeptId) {
        //查询所有后台节点
        SysDept deptInfo = getById(belongDeptId);
        if (deptInfo == null) {
            return Collections.emptyList();
        }
        String nodePath = deptInfo.getNodePath();
        SysDeptQueryVo q = new SysDeptQueryVo();
        q.setNodePathPrefix(nodePath);
        return list(q);
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