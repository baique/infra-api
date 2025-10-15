package tech.hljzj.infrastructure.vo.SysDept.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysDept;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 组织机构 sys_dept_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptQueryBaseVo extends PageDomain implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /** id_     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 上级组织     */
    private String parentId,parentIdNot,parentIdLike,parentIdPrefix,parentIdSuffix;
    private List<String> parentIdIn,parentIdNotIn;

    /** 本级节点标识     */
    private String nodeKey,nodeKeyNot,nodeKeyLike,nodeKeyPrefix,nodeKeySuffix;
    private List<String> nodeKeyIn,nodeKeyNotIn;

    /** 祖先节点路径     */
    private String nodePath,nodePathNot,nodePathLike,nodePathPrefix,nodePathSuffix;
    private List<String> nodePathIn,nodePathNotIn;

    /** 组织标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 组织名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 组织别名     */
    private String alias,aliasNot,aliasLike,aliasPrefix,aliasSuffix;
    private List<String> aliasIn,aliasNotIn;

    /** 组织简称     */
    private String shortName,shortNameNot,shortNameLike,shortNamePrefix,shortNameSuffix;
    private List<String> shortNameIn,shortNameNotIn;

    /** 组织类型     */
    private String type,typeNot,typeLike,typePrefix,typeSuffix;
    private List<String> typeIn,typeNotIn;

    /** 组织责任说明     */
    private String duty,dutyNot,dutyLike,dutyPrefix,dutySuffix;
    private List<String> dutyIn,dutyNotIn;

    /** 统一社会信用代码     */
    private String uscNo,uscNoNot,uscNoLike,uscNoPrefix,uscNoSuffix;
    private List<String> uscNoIn,uscNoNotIn;

    /** 所在地点     */
    private String addr,addrNot,addrLike,addrPrefix,addrSuffix;
    private List<String> addrIn,addrNotIn;

    /** 所在地点编码     */
    private String addrNo,addrNoNot,addrNoLike,addrNoPrefix,addrNoSuffix;
    private List<String> addrNoIn,addrNoNotIn;

    /** 状态     */
    private String status,statusNot,statusLike,statusPrefix,statusSuffix;
    private List<String> statusIn,statusNotIn;

    /** 是否启用     */
    private String enable,enableNot,enableLike,enablePrefix,enableSuffix;
    private List<String> enableIn,enableNotIn;

    /** 允许用户加入     */
    private String allowUserJoin,allowUserJoinNot,allowUserJoinLike,allowUserJoinPrefix,allowUserJoinSuffix;
    private List<String> allowUserJoinIn,allowUserJoinNotIn;

    /** 排序编号     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;

    /** 是否临时组织     */
    private String tmp,tmpNot,tmpLike,tmpPrefix,tmpSuffix;
    private List<String> tmpIn,tmpNotIn;


    
    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDept::getId, StrUtil.trim(this.getId()));
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

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionParentId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getParentId()),SysDept::getParentId, StrUtil.trim(this.getParentId()));
          builder.ne(StrUtil.isNotBlank(this.getParentIdNot()),T::getParentId, StrUtil.trim(this.getParentIdNot()));
          builder.in(null != this.getParentIdIn() && this.getParentIdIn().size() > 0,T::getParentId, this.getParentIdIn());
          builder.notIn(null != this.getParentIdNotIn() && this.getParentIdNotIn().size() > 0,T::getParentId, this.getParentIdNotIn());
          if (StrUtil.isNotBlank(this.getParentIdLike())) {
              builder.like(T::getParentId, StrUtil.trim(this.getParentIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getParentIdPrefix()),T::getParentId, StrUtil.trim(this.getParentIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getParentIdSuffix()),T::getParentId, StrUtil.trim(this.getParentIdSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionNodeKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getNodeKey()),SysDept::getNodeKey, StrUtil.trim(this.getNodeKey()));
          builder.ne(StrUtil.isNotBlank(this.getNodeKeyNot()),T::getNodeKey, StrUtil.trim(this.getNodeKeyNot()));
          builder.in(null != this.getNodeKeyIn() && this.getNodeKeyIn().size() > 0,T::getNodeKey, this.getNodeKeyIn());
          builder.notIn(null != this.getNodeKeyNotIn() && this.getNodeKeyNotIn().size() > 0,T::getNodeKey, this.getNodeKeyNotIn());
          if (StrUtil.isNotBlank(this.getNodeKeyLike())) {
              builder.like(T::getNodeKey, StrUtil.trim(this.getNodeKeyLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getNodeKeyPrefix()),T::getNodeKey, StrUtil.trim(this.getNodeKeyPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getNodeKeySuffix()),T::getNodeKey, StrUtil.trim(this.getNodeKeySuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionNodePath() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getNodePath()),SysDept::getNodePath, StrUtil.trim(this.getNodePath()));
          builder.ne(StrUtil.isNotBlank(this.getNodePathNot()),T::getNodePath, StrUtil.trim(this.getNodePathNot()));
          builder.in(null != this.getNodePathIn() && this.getNodePathIn().size() > 0,T::getNodePath, this.getNodePathIn());
          builder.notIn(null != this.getNodePathNotIn() && this.getNodePathNotIn().size() > 0,T::getNodePath, this.getNodePathNotIn());
          if (StrUtil.isNotBlank(this.getNodePathLike())) {
              builder.like(T::getNodePath, StrUtil.trim(this.getNodePathLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getNodePathPrefix()),T::getNodePath, StrUtil.trim(this.getNodePathPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getNodePathSuffix()),T::getNodePath, StrUtil.trim(this.getNodePathSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysDept::getKey, StrUtil.trim(this.getKey()));
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

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysDept::getName, StrUtil.trim(this.getName()));
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

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionAlias() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getAlias()),SysDept::getAlias, StrUtil.trim(this.getAlias()));
          builder.ne(StrUtil.isNotBlank(this.getAliasNot()),T::getAlias, StrUtil.trim(this.getAliasNot()));
          builder.in(null != this.getAliasIn() && this.getAliasIn().size() > 0,T::getAlias, this.getAliasIn());
          builder.notIn(null != this.getAliasNotIn() && this.getAliasNotIn().size() > 0,T::getAlias, this.getAliasNotIn());
          if (StrUtil.isNotBlank(this.getAliasLike())) {
              builder.like(T::getAlias, StrUtil.trim(this.getAliasLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getAliasPrefix()),T::getAlias, StrUtil.trim(this.getAliasPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getAliasSuffix()),T::getAlias, StrUtil.trim(this.getAliasSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionShortName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getShortName()),SysDept::getShortName, StrUtil.trim(this.getShortName()));
          builder.ne(StrUtil.isNotBlank(this.getShortNameNot()),T::getShortName, StrUtil.trim(this.getShortNameNot()));
          builder.in(null != this.getShortNameIn() && this.getShortNameIn().size() > 0,T::getShortName, this.getShortNameIn());
          builder.notIn(null != this.getShortNameNotIn() && this.getShortNameNotIn().size() > 0,T::getShortName, this.getShortNameNotIn());
          if (StrUtil.isNotBlank(this.getShortNameLike())) {
              builder.like(T::getShortName, StrUtil.trim(this.getShortNameLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getShortNamePrefix()),T::getShortName, StrUtil.trim(this.getShortNamePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getShortNameSuffix()),T::getShortName, StrUtil.trim(this.getShortNameSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionType() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getType()),SysDept::getType, StrUtil.trim(this.getType()));
          builder.ne(StrUtil.isNotBlank(this.getTypeNot()),T::getType, StrUtil.trim(this.getTypeNot()));
          builder.in(null != this.getTypeIn() && this.getTypeIn().size() > 0,T::getType, this.getTypeIn());
          builder.notIn(null != this.getTypeNotIn() && this.getTypeNotIn().size() > 0,T::getType, this.getTypeNotIn());
          if (StrUtil.isNotBlank(this.getTypeLike())) {
              builder.like(T::getType, StrUtil.trim(this.getTypeLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getTypePrefix()),T::getType, StrUtil.trim(this.getTypePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getTypeSuffix()),T::getType, StrUtil.trim(this.getTypeSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionDuty() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDuty()),SysDept::getDuty, StrUtil.trim(this.getDuty()));
          builder.ne(StrUtil.isNotBlank(this.getDutyNot()),T::getDuty, StrUtil.trim(this.getDutyNot()));
          builder.in(null != this.getDutyIn() && this.getDutyIn().size() > 0,T::getDuty, this.getDutyIn());
          builder.notIn(null != this.getDutyNotIn() && this.getDutyNotIn().size() > 0,T::getDuty, this.getDutyNotIn());
          if (StrUtil.isNotBlank(this.getDutyLike())) {
              builder.like(T::getDuty, StrUtil.trim(this.getDutyLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getDutyPrefix()),T::getDuty, StrUtil.trim(this.getDutyPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getDutySuffix()),T::getDuty, StrUtil.trim(this.getDutySuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionUscNo() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getUscNo()),SysDept::getUscNo, StrUtil.trim(this.getUscNo()));
          builder.ne(StrUtil.isNotBlank(this.getUscNoNot()),T::getUscNo, StrUtil.trim(this.getUscNoNot()));
          builder.in(null != this.getUscNoIn() && this.getUscNoIn().size() > 0,T::getUscNo, this.getUscNoIn());
          builder.notIn(null != this.getUscNoNotIn() && this.getUscNoNotIn().size() > 0,T::getUscNo, this.getUscNoNotIn());
          if (StrUtil.isNotBlank(this.getUscNoLike())) {
              builder.like(T::getUscNo, StrUtil.trim(this.getUscNoLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getUscNoPrefix()),T::getUscNo, StrUtil.trim(this.getUscNoPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getUscNoSuffix()),T::getUscNo, StrUtil.trim(this.getUscNoSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionAddr() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getAddr()),SysDept::getAddr, StrUtil.trim(this.getAddr()));
          builder.ne(StrUtil.isNotBlank(this.getAddrNot()),T::getAddr, StrUtil.trim(this.getAddrNot()));
          builder.in(null != this.getAddrIn() && this.getAddrIn().size() > 0,T::getAddr, this.getAddrIn());
          builder.notIn(null != this.getAddrNotIn() && this.getAddrNotIn().size() > 0,T::getAddr, this.getAddrNotIn());
          if (StrUtil.isNotBlank(this.getAddrLike())) {
              builder.like(T::getAddr, StrUtil.trim(this.getAddrLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getAddrPrefix()),T::getAddr, StrUtil.trim(this.getAddrPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getAddrSuffix()),T::getAddr, StrUtil.trim(this.getAddrSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionAddrNo() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getAddrNo()),SysDept::getAddrNo, StrUtil.trim(this.getAddrNo()));
          builder.ne(StrUtil.isNotBlank(this.getAddrNoNot()),T::getAddrNo, StrUtil.trim(this.getAddrNoNot()));
          builder.in(null != this.getAddrNoIn() && this.getAddrNoIn().size() > 0,T::getAddrNo, this.getAddrNoIn());
          builder.notIn(null != this.getAddrNoNotIn() && this.getAddrNoNotIn().size() > 0,T::getAddrNo, this.getAddrNoNotIn());
          if (StrUtil.isNotBlank(this.getAddrNoLike())) {
              builder.like(T::getAddrNo, StrUtil.trim(this.getAddrNoLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getAddrNoPrefix()),T::getAddrNo, StrUtil.trim(this.getAddrNoPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getAddrNoSuffix()),T::getAddrNo, StrUtil.trim(this.getAddrNoSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionStatus() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getStatus()),SysDept::getStatus, StrUtil.trim(this.getStatus()));
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

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionEnable() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getEnable()),SysDept::getEnable, StrUtil.trim(this.getEnable()));
          builder.ne(StrUtil.isNotBlank(this.getEnableNot()),T::getEnable, StrUtil.trim(this.getEnableNot()));
          builder.in(null != this.getEnableIn() && this.getEnableIn().size() > 0,T::getEnable, this.getEnableIn());
          builder.notIn(null != this.getEnableNotIn() && this.getEnableNotIn().size() > 0,T::getEnable, this.getEnableNotIn());
          if (StrUtil.isNotBlank(this.getEnableLike())) {
              builder.like(T::getEnable, StrUtil.trim(this.getEnableLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getEnablePrefix()),T::getEnable, StrUtil.trim(this.getEnablePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getEnableSuffix()),T::getEnable, StrUtil.trim(this.getEnableSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionAllowUserJoin() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getAllowUserJoin()),SysDept::getAllowUserJoin, StrUtil.trim(this.getAllowUserJoin()));
          builder.ne(StrUtil.isNotBlank(this.getAllowUserJoinNot()),T::getAllowUserJoin, StrUtil.trim(this.getAllowUserJoinNot()));
          builder.in(null != this.getAllowUserJoinIn() && this.getAllowUserJoinIn().size() > 0,T::getAllowUserJoin, this.getAllowUserJoinIn());
          builder.notIn(null != this.getAllowUserJoinNotIn() && this.getAllowUserJoinNotIn().size() > 0,T::getAllowUserJoin, this.getAllowUserJoinNotIn());
          if (StrUtil.isNotBlank(this.getAllowUserJoinLike())) {
              builder.like(T::getAllowUserJoin, StrUtil.trim(this.getAllowUserJoinLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getAllowUserJoinPrefix()),T::getAllowUserJoin, StrUtil.trim(this.getAllowUserJoinPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getAllowUserJoinSuffix()),T::getAllowUserJoin, StrUtil.trim(this.getAllowUserJoinSuffix()));
          }


        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysDept::getSort, (this.getSort()));
          builder.ne( null != this.getSortNot(),T::getSort, (this.getSortNot()));
          builder.in(null != this.getSortIn() && this.getSortIn().size() > 0,T::getSort, this.getSortIn());
          builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0,T::getSort, this.getSortNotIn());
          builder.gt(this.getSortGt() != null,T::getSort, this.getSortGt());
          builder.ge(this.getSortGte() != null,T::getSort, this.getSortGte());         
          builder.lt(this.getSortLt() != null,T::getSort, this.getSortLt());         
          builder.le(this.getSortLte() != null,T::getSort, this.getSortLte());

  
        };
    }

    public <T extends SysDept> Consumer<LambdaQueryWrapper<T>> conditionTmp() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getTmp()),SysDept::getTmp, StrUtil.trim(this.getTmp()));
          builder.ne(StrUtil.isNotBlank(this.getTmpNot()),T::getTmp, StrUtil.trim(this.getTmpNot()));
          builder.in(null != this.getTmpIn() && this.getTmpIn().size() > 0,T::getTmp, this.getTmpIn());
          builder.notIn(null != this.getTmpNotIn() && this.getTmpNotIn().size() > 0,T::getTmp, this.getTmpNotIn());
          if (StrUtil.isNotBlank(this.getTmpLike())) {
              builder.like(T::getTmp, StrUtil.trim(this.getTmpLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getTmpPrefix()),T::getTmp, StrUtil.trim(this.getTmpPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getTmpSuffix()),T::getTmp, StrUtil.trim(this.getTmpSuffix()));
          }


        };
    }

    /**
     * 构建查询条件
     */
    public <T extends SysDept> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionParentId().accept(builder);
        this.<T>conditionNodeKey().accept(builder);
        this.<T>conditionNodePath().accept(builder);
        this.<T>conditionKey().accept(builder);
        this.<T>conditionName().accept(builder);
        this.<T>conditionAlias().accept(builder);
        this.<T>conditionShortName().accept(builder);
        this.<T>conditionType().accept(builder);
        this.<T>conditionDuty().accept(builder);
        this.<T>conditionUscNo().accept(builder);
        this.<T>conditionAddr().accept(builder);
        this.<T>conditionAddrNo().accept(builder);
        this.<T>conditionStatus().accept(builder);
        this.<T>conditionEnable().accept(builder);
        this.<T>conditionAllowUserJoin().accept(builder);
        this.<T>conditionSort().accept(builder);
        this.<T>conditionTmp().accept(builder);
        return builder;
    }

}