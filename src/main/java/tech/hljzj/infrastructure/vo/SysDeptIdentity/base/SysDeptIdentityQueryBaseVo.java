package tech.hljzj.infrastructure.vo.SysDeptIdentity.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import tech.hljzj.infrastructure.domain.SysDeptIdentity;
import tech.hljzj.framework.pojo.form.PageDomain;

/**
 * 岗位管理 sys_dept_identity_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptIdentityQueryBaseVo<T extends SysDeptIdentity> extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /** id_     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 部门标识     */
    private String deptId,deptIdNot,deptIdLike,deptIdPrefix,deptIdSuffix;
    private List<String> deptIdIn,deptIdNotIn;

    /** 岗位标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 岗位名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 岗位顺序     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;

    /** 岗位描述     */
    private String desc,descNot,descLike,descPrefix,descSuffix;
    private List<String> descIn,descNotIn;


    
    public Consumer<LambdaQueryWrapper<? extends T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDeptIdentity::getId, StrUtil.trim(this.getId()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionDeptId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDeptId()),SysDeptIdentity::getDeptId, StrUtil.trim(this.getDeptId()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysDeptIdentity::getKey, StrUtil.trim(this.getKey()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysDeptIdentity::getName, StrUtil.trim(this.getName()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysDeptIdentity::getSort, (this.getSort()));
          builder.ne( null != this.getSortNot(),T::getSort, (this.getSortNot()));
          builder.in(null != this.getSortIn() && this.getSortIn().size() > 0,T::getSort, this.getSortIn());
          builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0,T::getSort, this.getSortNotIn());
          builder.gt(this.getSortGt() != null,T::getSort, this.getSortGt());
          builder.ge(this.getSortGte() != null,T::getSort, this.getSortGte());         
          builder.lt(this.getSortLt() != null,T::getSort, this.getSortLt());         
          builder.le(this.getSortLte() != null,T::getSort, this.getSortLte());

  
        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionDesc() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDesc()),SysDeptIdentity::getDesc, StrUtil.trim(this.getDesc()));
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

    /**
     * 默认排序
     */
    public Consumer<LambdaQueryWrapper<? extends T>> defaultSortBy() {
        return (builder) -> {
            builder.orderByDesc(T::getId);
        };
    }

    /**
     * 构建查询条件
     */
    public <R extends T> LambdaQueryWrapper<R> buildQueryWrapper() {
        LambdaQueryWrapper<R> builder = Wrappers.lambdaQuery();
        this.conditionId().accept(builder);
        this.conditionDeptId().accept(builder);
        this.conditionKey().accept(builder);
        this.conditionName().accept(builder);
        this.conditionSort().accept(builder);
        this.conditionDesc().accept(builder);
        this.defaultSortBy().accept(builder);
        return builder;
    }

}