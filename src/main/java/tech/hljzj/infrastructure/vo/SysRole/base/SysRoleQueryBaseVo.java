package tech.hljzj.infrastructure.vo.SysRole.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysRole;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 角色管理 sys_role_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysRoleQueryBaseVo extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /** bigint     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 所属应用标识     */
    private String ownerAppId,ownerAppIdNot,ownerAppIdLike,ownerAppIdPrefix,ownerAppIdSuffix;
    private List<String> ownerAppIdIn,ownerAppIdNotIn;

    /** 角色标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 角色名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 角色状态     */
    private String status,statusNot,statusLike,statusPrefix,statusSuffix;
    private List<String> statusIn,statusNotIn;

    /** 角色描述     */
    private String desc,descNot,descLike,descPrefix,descSuffix;
    private List<String> descIn,descNotIn;

    /** 默认授予登录用户     */
    private String defaultGrant,defaultGrantNot,defaultGrantLike,defaultGrantPrefix,defaultGrantSuffix;
    private List<String> defaultGrantIn,defaultGrantNotIn;

    /** 优先级（多个角色存在冲突时以哪个角色为主）     */
    private Integer priority,priorityNot,priorityGt,priorityGte,priorityLt,priorityLte;
    private List<Integer> priorityIn,priorityNotIn;

    /** 主页地址     */
    private String mainPagePath,mainPagePathNot,mainPagePathLike,mainPagePathPrefix,mainPagePathSuffix;
    private List<String> mainPagePathIn,mainPagePathNotIn;

    /** 排序编号     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;


    
    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysRole::getId, StrUtil.trim(this.getId()));
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

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionOwnerAppId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getOwnerAppId()),SysRole::getOwnerAppId, StrUtil.trim(this.getOwnerAppId()));
          builder.ne(StrUtil.isNotBlank(this.getOwnerAppIdNot()),T::getOwnerAppId, StrUtil.trim(this.getOwnerAppIdNot()));
          builder.in(null != this.getOwnerAppIdIn() && this.getOwnerAppIdIn().size() > 0,T::getOwnerAppId, this.getOwnerAppIdIn());
          builder.notIn(null != this.getOwnerAppIdNotIn() && this.getOwnerAppIdNotIn().size() > 0,T::getOwnerAppId, this.getOwnerAppIdNotIn());
          if (StrUtil.isNotBlank(this.getOwnerAppIdLike())) {
              builder.like(T::getOwnerAppId, StrUtil.trim(this.getOwnerAppIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getOwnerAppIdPrefix()),T::getOwnerAppId, StrUtil.trim(this.getOwnerAppIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getOwnerAppIdSuffix()),T::getOwnerAppId, StrUtil.trim(this.getOwnerAppIdSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysRole::getKey, StrUtil.trim(this.getKey()));
          builder.ne(StrUtil.isNotBlank(this.getKeyNot()),T::getKey, StrUtil.trim(this.getKeyNot()));
          builder.in(null != this.getKeyIn() && this.getKeyIn().size() > 0,T::getKey, this.getKeyIn());
          builder.notIn(null != this.getKeyNotIn() && this.getKeyNotIn().size() > 0,T::getKey, this.getKeyNotIn());
          if (StrUtil.isNotBlank(this.getKeyLike())) {
              builder.like(T::getKey, StrUtil.trim(this.getKeyLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getKeyPrefix()),T::getKey, StrUtil.trim(this.getKeyPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getKeySuffix()),T::getKey, StrUtil.trim(this.getKeySuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysRole::getName, StrUtil.trim(this.getName()));
          builder.ne(StrUtil.isNotBlank(this.getNameNot()),T::getName, StrUtil.trim(this.getNameNot()));
          builder.in(null != this.getNameIn() && this.getNameIn().size() > 0,T::getName, this.getNameIn());
          builder.notIn(null != this.getNameNotIn() && this.getNameNotIn().size() > 0,T::getName, this.getNameNotIn());
          if (StrUtil.isNotBlank(this.getNameLike())) {
              builder.like(T::getName, StrUtil.trim(this.getNameLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getNamePrefix()),T::getName, StrUtil.trim(this.getNamePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getNameSuffix()),T::getName, StrUtil.trim(this.getNameSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionStatus() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getStatus()),SysRole::getStatus, StrUtil.trim(this.getStatus()));
          builder.ne(StrUtil.isNotBlank(this.getStatusNot()),T::getStatus, StrUtil.trim(this.getStatusNot()));
          builder.in(null != this.getStatusIn() && this.getStatusIn().size() > 0,T::getStatus, this.getStatusIn());
          builder.notIn(null != this.getStatusNotIn() && this.getStatusNotIn().size() > 0,T::getStatus, this.getStatusNotIn());
          if (StrUtil.isNotBlank(this.getStatusLike())) {
              builder.like(T::getStatus, StrUtil.trim(this.getStatusLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getStatusPrefix()),T::getStatus, StrUtil.trim(this.getStatusPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getStatusSuffix()),T::getStatus, StrUtil.trim(this.getStatusSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionDesc() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDesc()),SysRole::getDesc, StrUtil.trim(this.getDesc()));
          builder.ne(StrUtil.isNotBlank(this.getDescNot()),T::getDesc, StrUtil.trim(this.getDescNot()));
          builder.in(null != this.getDescIn() && this.getDescIn().size() > 0,T::getDesc, this.getDescIn());
          builder.notIn(null != this.getDescNotIn() && this.getDescNotIn().size() > 0,T::getDesc, this.getDescNotIn());
          if (StrUtil.isNotBlank(this.getDescLike())) {
              builder.like(T::getDesc, StrUtil.trim(this.getDescLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getDescPrefix()),T::getDesc, StrUtil.trim(this.getDescPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getDescSuffix()),T::getDesc, StrUtil.trim(this.getDescSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionDefaultGrant() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDefaultGrant()),T::getDefaultGrant, StrUtil.trim(this.getDefaultGrant()));
          builder.ne(StrUtil.isNotBlank(this.getDefaultGrantNot()),T::getDefaultGrant, StrUtil.trim(this.getDefaultGrantNot()));
          builder.in(null != this.getDefaultGrantIn() && this.getDefaultGrantIn().size() > 0,T::getDefaultGrant, this.getDefaultGrantIn());
          builder.notIn(null != this.getDefaultGrantNotIn() && this.getDefaultGrantNotIn().size() > 0,T::getDefaultGrant, this.getDefaultGrantNotIn());
          if (StrUtil.isNotBlank(this.getDefaultGrantLike())) {
              builder.like(T::getDefaultGrant, StrUtil.trim(this.getDefaultGrantLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getDefaultGrantPrefix()),T::getDefaultGrant, StrUtil.trim(this.getDefaultGrantPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getDefaultGrantSuffix()),T::getDefaultGrant, StrUtil.trim(this.getDefaultGrantSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionPriority() {
        return (builder)->{

          builder.eq( null != this.getPriority(),SysRole::getPriority, (this.getPriority()));
          builder.ne( null != this.getPriorityNot(),T::getPriority, (this.getPriorityNot()));
          builder.in(null != this.getPriorityIn() && this.getPriorityIn().size() > 0,T::getPriority, this.getPriorityIn());
          builder.notIn(null != this.getPriorityNotIn() && this.getPriorityNotIn().size() > 0,T::getPriority, this.getPriorityNotIn());
          builder.gt(this.getPriorityGt() != null,T::getPriority, this.getPriorityGt());
          builder.ge(this.getPriorityGte() != null,T::getPriority, this.getPriorityGte());         
          builder.lt(this.getPriorityLt() != null,T::getPriority, this.getPriorityLt());         
          builder.le(this.getPriorityLte() != null,T::getPriority, this.getPriorityLte());

  
        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionMainPagePath() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getMainPagePath()),SysRole::getMainPagePath, StrUtil.trim(this.getMainPagePath()));
          builder.ne(StrUtil.isNotBlank(this.getMainPagePathNot()),T::getMainPagePath, StrUtil.trim(this.getMainPagePathNot()));
          builder.in(null != this.getMainPagePathIn() && this.getMainPagePathIn().size() > 0,T::getMainPagePath, this.getMainPagePathIn());
          builder.notIn(null != this.getMainPagePathNotIn() && this.getMainPagePathNotIn().size() > 0,T::getMainPagePath, this.getMainPagePathNotIn());
          if (StrUtil.isNotBlank(this.getMainPagePathLike())) {
              builder.like(T::getMainPagePath, StrUtil.trim(this.getMainPagePathLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getMainPagePathPrefix()),T::getMainPagePath, StrUtil.trim(this.getMainPagePathPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getMainPagePathSuffix()),T::getMainPagePath, StrUtil.trim(this.getMainPagePathSuffix()));
          }


        };
    }

    public <T extends SysRole> Consumer<LambdaQueryWrapper<T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysRole::getSort, (this.getSort()));
          builder.ne( null != this.getSortNot(),T::getSort, (this.getSortNot()));
          builder.in(null != this.getSortIn() && this.getSortIn().size() > 0,T::getSort, this.getSortIn());
          builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0,T::getSort, this.getSortNotIn());
          builder.gt(this.getSortGt() != null,T::getSort, this.getSortGt());
          builder.ge(this.getSortGte() != null,T::getSort, this.getSortGte());         
          builder.lt(this.getSortLt() != null,T::getSort, this.getSortLt());         
          builder.le(this.getSortLte() != null,T::getSort, this.getSortLte());

  
        };
    }

    /**
     * 构建查询条件
     */
    public <T extends SysRole> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.<T>lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionOwnerAppId().accept(builder);
        this.<T>conditionKey().accept(builder);
        this.<T>conditionName().accept(builder);
        this.<T>conditionStatus().accept(builder);
        this.<T>conditionDesc().accept(builder);
        this.<T>conditionDefaultGrant().accept(builder);
        this.<T>conditionPriority().accept(builder);
        this.<T>conditionMainPagePath().accept(builder);
        this.<T>conditionSort().accept(builder);
        return builder;
    }

}