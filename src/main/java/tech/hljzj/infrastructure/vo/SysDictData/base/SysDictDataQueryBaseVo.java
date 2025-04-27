package tech.hljzj.infrastructure.vo.SysDictData.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.framework.pojo.form.PageDomain;

/**
 * 字典项 sys_dict_data_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictDataQueryBaseVo extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /** bigint     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 所属应用标识     */
    private String ownerAppId,ownerAppIdNot,ownerAppIdLike,ownerAppIdPrefix,ownerAppIdSuffix;
    private List<String> ownerAppIdIn,ownerAppIdNotIn;

    /** 所属字典类型标识     */
    private String ownerTypeId,ownerTypeIdNot,ownerTypeIdLike,ownerTypeIdPrefix,ownerTypeIdSuffix;
    private List<String> ownerTypeIdIn,ownerTypeIdNotIn;

    /** 字典标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 字典名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 字典项值     */
    private String value,valueNot,valueLike,valuePrefix,valueSuffix;
    private List<String> valueIn,valueNotIn;

    /** 数据样式     */
    private String listClass,listClassNot,listClassLike,listClassPrefix,listClassSuffix;
    private List<String> listClassIn,listClassNotIn;

    /** 是否可选     */
    private String selectable,selectableNot,selectableLike,selectablePrefix,selectableSuffix;
    private List<String> selectableIn,selectableNotIn;

    /** 字典状态     */
    private String status,statusNot,statusLike,statusPrefix,statusSuffix;
    private List<String> statusIn,statusNotIn;

    /** 选项说明     */
    private String helpMessage,helpMessageNot,helpMessageLike,helpMessagePrefix,helpMessageSuffix;
    private List<String> helpMessageIn,helpMessageNotIn;

    /** 字典描述     */
    private String desc,descNot,descLike,descPrefix,descSuffix;
    private List<String> descIn,descNotIn;

    /** 是否锁定     */
    private String locked,lockedNot,lockedLike,lockedPrefix,lockedSuffix;
    private List<String> lockedIn,lockedNotIn;

    /** 排序编号     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;


    
    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDictData::getId, StrUtil.trim(this.getId()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionOwnerAppId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getOwnerAppId()),SysDictData::getOwnerAppId, StrUtil.trim(this.getOwnerAppId()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionOwnerTypeId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getOwnerTypeId()),SysDictData::getOwnerTypeId, StrUtil.trim(this.getOwnerTypeId()));
          builder.ne(StrUtil.isNotBlank(this.getOwnerTypeIdNot()),T::getOwnerTypeId, StrUtil.trim(this.getOwnerTypeIdNot()));
          builder.in(null != this.getOwnerTypeIdIn() && this.getOwnerTypeIdIn().size() > 0,T::getOwnerTypeId, this.getOwnerTypeIdIn());
          builder.notIn(null != this.getOwnerTypeIdNotIn() && this.getOwnerTypeIdNotIn().size() > 0,T::getOwnerTypeId, this.getOwnerTypeIdNotIn());
          if (StrUtil.isNotBlank(this.getOwnerTypeIdLike())) {
              builder.like(T::getOwnerTypeId, StrUtil.trim(this.getOwnerTypeIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getOwnerTypeIdPrefix()),T::getOwnerTypeId, StrUtil.trim(this.getOwnerTypeIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getOwnerTypeIdSuffix()),T::getOwnerTypeId, StrUtil.trim(this.getOwnerTypeIdSuffix()));
          }


        };
    }

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysDictData::getKey, StrUtil.trim(this.getKey()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysDictData::getName, StrUtil.trim(this.getName()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionValue() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getValue()),SysDictData::getValue, StrUtil.trim(this.getValue()));
          builder.ne(StrUtil.isNotBlank(this.getValueNot()),T::getValue, StrUtil.trim(this.getValueNot()));
          builder.in(null != this.getValueIn() && this.getValueIn().size() > 0,T::getValue, this.getValueIn());
          builder.notIn(null != this.getValueNotIn() && this.getValueNotIn().size() > 0,T::getValue, this.getValueNotIn());
          if (StrUtil.isNotBlank(this.getValueLike())) {
              builder.like(T::getValue, StrUtil.trim(this.getValueLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getValuePrefix()),T::getValue, StrUtil.trim(this.getValuePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getValueSuffix()),T::getValue, StrUtil.trim(this.getValueSuffix()));
          }


        };
    }

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionListClass() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getListClass()),SysDictData::getListClass, StrUtil.trim(this.getListClass()));
          builder.ne(StrUtil.isNotBlank(this.getListClassNot()),T::getListClass, StrUtil.trim(this.getListClassNot()));
          builder.in(null != this.getListClassIn() && this.getListClassIn().size() > 0,T::getListClass, this.getListClassIn());
          builder.notIn(null != this.getListClassNotIn() && this.getListClassNotIn().size() > 0,T::getListClass, this.getListClassNotIn());
          if (StrUtil.isNotBlank(this.getListClassLike())) {
              builder.like(T::getListClass, StrUtil.trim(this.getListClassLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getListClassPrefix()),T::getListClass, StrUtil.trim(this.getListClassPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getListClassSuffix()),T::getListClass, StrUtil.trim(this.getListClassSuffix()));
          }


        };
    }

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionStatus() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getStatus()),SysDictData::getStatus, StrUtil.trim(this.getStatus()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionDesc() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDesc()),SysDictData::getDesc, StrUtil.trim(this.getDesc()));
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

    public <T extends SysDictData> Consumer<LambdaQueryWrapper<T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysDictData::getSort, (this.getSort()));
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
    public <T extends SysDictData> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.<T>lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionOwnerAppId().accept(builder);
        this.<T>conditionOwnerTypeId().accept(builder);
        this.<T>conditionKey().accept(builder);
        this.<T>conditionName().accept(builder);
        this.<T>conditionValue().accept(builder);
        this.<T>conditionListClass().accept(builder);
        this.<T>conditionStatus().accept(builder);
        this.<T>conditionDesc().accept(builder);
        this.<T>conditionSort().accept(builder);
        return builder;
    }

}