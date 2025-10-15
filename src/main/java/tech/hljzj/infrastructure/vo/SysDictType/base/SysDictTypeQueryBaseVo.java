package tech.hljzj.infrastructure.vo.SysDictType.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysDictType;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 字典组 sys_dict_type_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictTypeQueryBaseVo extends PageDomain implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /** bigint     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 所属应用标识     */
    private String ownerAppId,ownerAppIdNot,ownerAppIdLike,ownerAppIdPrefix,ownerAppIdSuffix;
    private List<String> ownerAppIdIn,ownerAppIdNotIn;

    /** 字典类型标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 字典类型名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 字典类型状态     */
    private String status,statusNot,statusLike,statusPrefix,statusSuffix;
    private List<String> statusIn,statusNotIn;

    /** 值长度限制     */
    private Integer maxLength,maxLengthNot,maxLengthGt,maxLengthGte,maxLengthLt,maxLengthLte;
    private List<Integer> maxLengthIn,maxLengthNotIn;

    /** 字典说明     */
    private String helpMessage,helpMessageNot,helpMessageLike,helpMessagePrefix,helpMessageSuffix;
    private List<String> helpMessageIn,helpMessageNotIn;

    /** 字典类型描述     */
    private String desc,descNot,descLike,descPrefix,descSuffix;
    private List<String> descIn,descNotIn;

    /** 排序编号     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;


    
    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDictType::getId, StrUtil.trim(this.getId()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionOwnerAppId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getOwnerAppId()),SysDictType::getOwnerAppId, StrUtil.trim(this.getOwnerAppId()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysDictType::getKey, StrUtil.trim(this.getKey()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysDictType::getName, StrUtil.trim(this.getName()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionStatus() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getStatus()),SysDictType::getStatus, StrUtil.trim(this.getStatus()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionMaxLength() {
        return (builder)->{

          builder.eq( null != this.getMaxLength(),SysDictType::getMaxLength, (this.getMaxLength()));
          builder.ne( null != this.getMaxLengthNot(),T::getMaxLength, (this.getMaxLengthNot()));
          builder.in(null != this.getMaxLengthIn() && this.getMaxLengthIn().size() > 0,T::getMaxLength, this.getMaxLengthIn());
          builder.notIn(null != this.getMaxLengthNotIn() && this.getMaxLengthNotIn().size() > 0,T::getMaxLength, this.getMaxLengthNotIn());
          builder.gt(this.getMaxLengthGt() != null,T::getMaxLength, this.getMaxLengthGt());
          builder.ge(this.getMaxLengthGte() != null,T::getMaxLength, this.getMaxLengthGte());         
          builder.lt(this.getMaxLengthLt() != null,T::getMaxLength, this.getMaxLengthLt());         
          builder.le(this.getMaxLengthLte() != null,T::getMaxLength, this.getMaxLengthLte());

  
        };
    }

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionHelpMessage() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getHelpMessage()),SysDictType::getHelpMessage, StrUtil.trim(this.getHelpMessage()));
          builder.ne(StrUtil.isNotBlank(this.getHelpMessageNot()),T::getHelpMessage, StrUtil.trim(this.getHelpMessageNot()));
          builder.in(null != this.getHelpMessageIn() && this.getHelpMessageIn().size() > 0,T::getHelpMessage, this.getHelpMessageIn());
          builder.notIn(null != this.getHelpMessageNotIn() && this.getHelpMessageNotIn().size() > 0,T::getHelpMessage, this.getHelpMessageNotIn());
          if (StrUtil.isNotBlank(this.getHelpMessageLike())) {
              builder.like(T::getHelpMessage, StrUtil.trim(this.getHelpMessageLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getHelpMessagePrefix()),T::getHelpMessage, StrUtil.trim(this.getHelpMessagePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getHelpMessageSuffix()),T::getHelpMessage, StrUtil.trim(this.getHelpMessageSuffix()));
          }


        };
    }

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionDesc() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDesc()),SysDictType::getDesc, StrUtil.trim(this.getDesc()));
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

    public <T extends SysDictType> Consumer<LambdaQueryWrapper<T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysDictType::getSort, (this.getSort()));
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
    public <T extends SysDictType> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionOwnerAppId().accept(builder);
        this.<T>conditionKey().accept(builder);
        this.<T>conditionName().accept(builder);
        this.<T>conditionStatus().accept(builder);
        this.<T>conditionMaxLength().accept(builder);
        this.<T>conditionHelpMessage().accept(builder);
        this.<T>conditionDesc().accept(builder);
        this.<T>conditionSort().accept(builder);
        return builder;
    }

}