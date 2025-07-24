package tech.hljzj.infrastructure.vo.SysDeptExternalUser.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import tech.hljzj.infrastructure.domain.SysDeptExternalUser;
import tech.hljzj.framework.pojo.form.PageDomain;

/**
 * 外部编入人员 sys_dept_external_user_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptExternalUserQueryBaseVo extends PageDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /** id_     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 部门标识     */
    private String deptId,deptIdNot,deptIdLike,deptIdPrefix,deptIdSuffix;
    private List<String> deptIdIn,deptIdNotIn;

    /** 用户标识     */
    private String userId,userIdNot,userIdLike,userIdPrefix,userIdSuffix;
    private List<String> userIdIn,userIdNotIn;

    /** 用户身份     */
    private String identity,identityNot,identityLike,identityPrefix,identitySuffix;
    private List<String> identityIn,identityNotIn;

    /** 备注信息     */
    private String remarks,remarksNot,remarksLike,remarksPrefix,remarksSuffix;
    private List<String> remarksIn,remarksNotIn;


    
    public <T extends SysDeptExternalUser> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDeptExternalUser::getId, StrUtil.trim(this.getId()));
          builder.ne(StrUtil.isNotBlank(this.getIdNot()),T::getId, StrUtil.trim(this.getIdNot()));
          builder.in(null != this.getIdIn() && this.getIdIn().size() > 0,T::getId, this.getIdIn());
          builder.notIn(null != this.getIdNotIn() && this.getIdNotIn().size() > 0,T::getId, this.getIdNotIn());
          if (StrUtil.isNotBlank(this.getIdLike())) {
              builder.like(T::getId, StrUtil.trim(this.getIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getIdPrefix()),T::getId, StrUtil.trim(this.getIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getIdSuffix()),T::getId, StrUtil.trim(this.getIdSuffix()));
          }


        };
    }

    public <T extends SysDeptExternalUser> Consumer<LambdaQueryWrapper<T>> conditionDeptId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDeptId()),SysDeptExternalUser::getDeptId, StrUtil.trim(this.getDeptId()));
          builder.ne(StrUtil.isNotBlank(this.getDeptIdNot()),T::getDeptId, StrUtil.trim(this.getDeptIdNot()));
          builder.in(null != this.getDeptIdIn() && this.getDeptIdIn().size() > 0,T::getDeptId, this.getDeptIdIn());
          builder.notIn(null != this.getDeptIdNotIn() && this.getDeptIdNotIn().size() > 0,T::getDeptId, this.getDeptIdNotIn());
          if (StrUtil.isNotBlank(this.getDeptIdLike())) {
              builder.like(T::getDeptId, StrUtil.trim(this.getDeptIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getDeptIdPrefix()),T::getDeptId, StrUtil.trim(this.getDeptIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getDeptIdSuffix()),T::getDeptId, StrUtil.trim(this.getDeptIdSuffix()));
          }


        };
    }

    public <T extends SysDeptExternalUser> Consumer<LambdaQueryWrapper<T>> conditionUserId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getUserId()),SysDeptExternalUser::getUserId, StrUtil.trim(this.getUserId()));
          builder.ne(StrUtil.isNotBlank(this.getUserIdNot()),T::getUserId, StrUtil.trim(this.getUserIdNot()));
          builder.in(null != this.getUserIdIn() && this.getUserIdIn().size() > 0,T::getUserId, this.getUserIdIn());
          builder.notIn(null != this.getUserIdNotIn() && this.getUserIdNotIn().size() > 0,T::getUserId, this.getUserIdNotIn());
          if (StrUtil.isNotBlank(this.getUserIdLike())) {
              builder.like(T::getUserId, StrUtil.trim(this.getUserIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getUserIdPrefix()),T::getUserId, StrUtil.trim(this.getUserIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getUserIdSuffix()),T::getUserId, StrUtil.trim(this.getUserIdSuffix()));
          }


        };
    }

    public <T extends SysDeptExternalUser> Consumer<LambdaQueryWrapper<T>> conditionIdentity() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getIdentity()),SysDeptExternalUser::getIdentity, StrUtil.trim(this.getIdentity()));
          builder.ne(StrUtil.isNotBlank(this.getIdentityNot()),T::getIdentity, StrUtil.trim(this.getIdentityNot()));
          builder.in(null != this.getIdentityIn() && this.getIdentityIn().size() > 0,T::getIdentity, this.getIdentityIn());
          builder.notIn(null != this.getIdentityNotIn() && this.getIdentityNotIn().size() > 0,T::getIdentity, this.getIdentityNotIn());
          if (StrUtil.isNotBlank(this.getIdentityLike())) {
              builder.like(T::getIdentity, StrUtil.trim(this.getIdentityLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getIdentityPrefix()),T::getIdentity, StrUtil.trim(this.getIdentityPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getIdentitySuffix()),T::getIdentity, StrUtil.trim(this.getIdentitySuffix()));
          }


        };
    }

    public <T extends SysDeptExternalUser> Consumer<LambdaQueryWrapper<T>> conditionRemarks() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getRemarks()),SysDeptExternalUser::getRemarks, StrUtil.trim(this.getRemarks()));
          builder.ne(StrUtil.isNotBlank(this.getRemarksNot()),T::getRemarks, StrUtil.trim(this.getRemarksNot()));
          builder.in(null != this.getRemarksIn() && this.getRemarksIn().size() > 0,T::getRemarks, this.getRemarksIn());
          builder.notIn(null != this.getRemarksNotIn() && this.getRemarksNotIn().size() > 0,T::getRemarks, this.getRemarksNotIn());
          if (StrUtil.isNotBlank(this.getRemarksLike())) {
              builder.like(T::getRemarks, StrUtil.trim(this.getRemarksLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getRemarksPrefix()),T::getRemarks, StrUtil.trim(this.getRemarksPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getRemarksSuffix()),T::getRemarks, StrUtil.trim(this.getRemarksSuffix()));
          }


        };
    }

    /**
     * 构建查询条件
     */
    public <T extends SysDeptExternalUser> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionDeptId().accept(builder);
        this.<T>conditionUserId().accept(builder);
        this.<T>conditionIdentity().accept(builder);
        this.<T>conditionRemarks().accept(builder);
        return builder;
    }

}