package tech.hljzj.infrastructure.vo.SysMenu.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.framework.pojo.form.PageDomain;

/**
 * 菜单管理 sys_menu_ 
 * 交互实体 用于检索
 *
 * @author 
 */
@Getter
@Setter
public class SysMenuQueryBaseVo extends PageDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /** bigint     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 所属应用标识     */
    private String ownerAppId,ownerAppIdNot,ownerAppIdLike,ownerAppIdPrefix,ownerAppIdSuffix;
    private List<String> ownerAppIdIn,ownerAppIdNotIn;

    /** 节点标识     */
    private String nodeKey,nodeKeyNot,nodeKeyLike,nodeKeyPrefix,nodeKeySuffix;
    private List<String> nodeKeyIn,nodeKeyNotIn;

    /** 节点路径     */
    private String nodePath,nodePathNot,nodePathLike,nodePathPrefix,nodePathSuffix;
    private List<String> nodePathIn,nodePathNotIn;

    /** 菜单标识     */
    private String key,keyNot,keyLike,keyPrefix,keySuffix;
    private List<String> keyIn,keyNotIn;

    /** 菜单名称     */
    private String name,nameNot,nameLike,namePrefix,nameSuffix;
    private List<String> nameIn,nameNotIn;

    /** 菜单状态     */
    private String status,statusNot,statusLike,statusPrefix,statusSuffix;
    private List<String> statusIn,statusNotIn;

    /** 菜单描述     */
    private String desc,descNot,descLike,descPrefix,descSuffix;
    private List<String> descIn,descNotIn;

    /** 默认授予登录用户     */
    private String defaultGrant,defaultGrantNot,defaultGrantLike,defaultGrantPrefix,defaultGrantSuffix;
    private List<String> defaultGrantIn,defaultGrantNotIn;

    /** 显示状态     */
    private String visible,visibleNot,visibleLike,visiblePrefix,visibleSuffix;
    private List<String> visibleIn,visibleNotIn;

    /** 菜单类型     */
    private String menuType,menuTypeNot,menuTypeLike,menuTypePrefix,menuTypeSuffix;
    private List<String> menuTypeIn,menuTypeNotIn;

    /** 访问路径     */
    private String path,pathNot,pathLike,pathPrefix,pathSuffix;
    private List<String> pathIn,pathNotIn;

    /** 引用组件     */
    private String component,componentNot,componentLike,componentPrefix,componentSuffix;
    private List<String> componentIn,componentNotIn;

    /** 菜单图标     */
    private String icon,iconNot,iconLike,iconPrefix,iconSuffix;
    private List<String> iconIn,iconNotIn;

    /** 标签     */
    private String tag,tagNot,tagLike,tagPrefix,tagSuffix;
    private List<String> tagIn,tagNotIn;

    /** 类型     */
    private String classes,classesNot,classesLike,classesPrefix,classesSuffix;
    private List<String> classesIn,classesNotIn;

    /** 排序编号     */
    private Integer sort,sortNot,sortGt,sortGte,sortLt,sortLte;
    private List<Integer> sortIn,sortNotIn;

    /** 上级权限标识     */
    private String parentId,parentIdNot,parentIdLike,parentIdPrefix,parentIdSuffix;
    private List<String> parentIdIn,parentIdNotIn;


    
    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysMenu::getId, StrUtil.trim(this.getId()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionOwnerAppId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getOwnerAppId()),SysMenu::getOwnerAppId, StrUtil.trim(this.getOwnerAppId()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionNodeKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getNodeKey()),SysMenu::getNodeKey, StrUtil.trim(this.getNodeKey()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionNodePath() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getNodePath()),SysMenu::getNodePath, StrUtil.trim(this.getNodePath()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionKey() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getKey()),SysMenu::getKey, StrUtil.trim(this.getKey()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionName() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getName()),SysMenu::getName, StrUtil.trim(this.getName()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionStatus() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getStatus()),SysMenu::getStatus, StrUtil.trim(this.getStatus()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionDesc() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDesc()),SysMenu::getDesc, StrUtil.trim(this.getDesc()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionDefaultGrant() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getDefaultGrant()),SysMenu::getDefaultGrant, StrUtil.trim(this.getDefaultGrant()));
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

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionVisible() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getVisible()),SysMenu::getVisible, StrUtil.trim(this.getVisible()));
          builder.ne(StrUtil.isNotBlank(this.getVisibleNot()),T::getVisible, StrUtil.trim(this.getVisibleNot()));
          builder.in(null != this.getVisibleIn() && this.getVisibleIn().size() > 0,T::getVisible, this.getVisibleIn());
          builder.notIn(null != this.getVisibleNotIn() && this.getVisibleNotIn().size() > 0,T::getVisible, this.getVisibleNotIn());
          if (StrUtil.isNotBlank(this.getVisibleLike())) {
              builder.like(T::getVisible, StrUtil.trim(this.getVisibleLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getVisiblePrefix()),T::getVisible, StrUtil.trim(this.getVisiblePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getVisibleSuffix()),T::getVisible, StrUtil.trim(this.getVisibleSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionMenuType() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getMenuType()),SysMenu::getMenuType, StrUtil.trim(this.getMenuType()));
          builder.ne(StrUtil.isNotBlank(this.getMenuTypeNot()),T::getMenuType, StrUtil.trim(this.getMenuTypeNot()));
          builder.in(null != this.getMenuTypeIn() && this.getMenuTypeIn().size() > 0,T::getMenuType, this.getMenuTypeIn());
          builder.notIn(null != this.getMenuTypeNotIn() && this.getMenuTypeNotIn().size() > 0,T::getMenuType, this.getMenuTypeNotIn());
          if (StrUtil.isNotBlank(this.getMenuTypeLike())) {
              builder.like(T::getMenuType, StrUtil.trim(this.getMenuTypeLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getMenuTypePrefix()),T::getMenuType, StrUtil.trim(this.getMenuTypePrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getMenuTypeSuffix()),T::getMenuType, StrUtil.trim(this.getMenuTypeSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionPath() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getPath()),SysMenu::getPath, StrUtil.trim(this.getPath()));
          builder.ne(StrUtil.isNotBlank(this.getPathNot()),T::getPath, StrUtil.trim(this.getPathNot()));
          builder.in(null != this.getPathIn() && this.getPathIn().size() > 0,T::getPath, this.getPathIn());
          builder.notIn(null != this.getPathNotIn() && this.getPathNotIn().size() > 0,T::getPath, this.getPathNotIn());
          if (StrUtil.isNotBlank(this.getPathLike())) {
              builder.like(T::getPath, StrUtil.trim(this.getPathLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getPathPrefix()),T::getPath, StrUtil.trim(this.getPathPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getPathSuffix()),T::getPath, StrUtil.trim(this.getPathSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionComponent() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getComponent()),SysMenu::getComponent, StrUtil.trim(this.getComponent()));
          builder.ne(StrUtil.isNotBlank(this.getComponentNot()),T::getComponent, StrUtil.trim(this.getComponentNot()));
          builder.in(null != this.getComponentIn() && this.getComponentIn().size() > 0,T::getComponent, this.getComponentIn());
          builder.notIn(null != this.getComponentNotIn() && this.getComponentNotIn().size() > 0,T::getComponent, this.getComponentNotIn());
          if (StrUtil.isNotBlank(this.getComponentLike())) {
              builder.like(T::getComponent, StrUtil.trim(this.getComponentLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getComponentPrefix()),T::getComponent, StrUtil.trim(this.getComponentPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getComponentSuffix()),T::getComponent, StrUtil.trim(this.getComponentSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionIcon() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getIcon()),SysMenu::getIcon, StrUtil.trim(this.getIcon()));
          builder.ne(StrUtil.isNotBlank(this.getIconNot()),T::getIcon, StrUtil.trim(this.getIconNot()));
          builder.in(null != this.getIconIn() && this.getIconIn().size() > 0,T::getIcon, this.getIconIn());
          builder.notIn(null != this.getIconNotIn() && this.getIconNotIn().size() > 0,T::getIcon, this.getIconNotIn());
          if (StrUtil.isNotBlank(this.getIconLike())) {
              builder.like(T::getIcon, StrUtil.trim(this.getIconLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getIconPrefix()),T::getIcon, StrUtil.trim(this.getIconPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getIconSuffix()),T::getIcon, StrUtil.trim(this.getIconSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionTag() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getTag()),SysMenu::getTag, StrUtil.trim(this.getTag()));
          builder.ne(StrUtil.isNotBlank(this.getTagNot()),T::getTag, StrUtil.trim(this.getTagNot()));
          builder.in(null != this.getTagIn() && this.getTagIn().size() > 0,T::getTag, this.getTagIn());
          builder.notIn(null != this.getTagNotIn() && this.getTagNotIn().size() > 0,T::getTag, this.getTagNotIn());
          if (StrUtil.isNotBlank(this.getTagLike())) {
              builder.like(T::getTag, StrUtil.trim(this.getTagLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getTagPrefix()),T::getTag, StrUtil.trim(this.getTagPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getTagSuffix()),T::getTag, StrUtil.trim(this.getTagSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionClasses() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getClasses()),SysMenu::getClasses, StrUtil.trim(this.getClasses()));
          builder.ne(StrUtil.isNotBlank(this.getClassesNot()),T::getClasses, StrUtil.trim(this.getClassesNot()));
          builder.in(null != this.getClassesIn() && this.getClassesIn().size() > 0,T::getClasses, this.getClassesIn());
          builder.notIn(null != this.getClassesNotIn() && this.getClassesNotIn().size() > 0,T::getClasses, this.getClassesNotIn());
          if (StrUtil.isNotBlank(this.getClassesLike())) {
              builder.like(T::getClasses, StrUtil.trim(this.getClassesLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getClassesPrefix()),T::getClasses, StrUtil.trim(this.getClassesPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getClassesSuffix()),T::getClasses, StrUtil.trim(this.getClassesSuffix()));
          }


        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionSort() {
        return (builder)->{

          builder.eq( null != this.getSort(),SysMenu::getSort, (this.getSort()));
          builder.ne( null != this.getSortNot(),T::getSort, (this.getSortNot()));
          builder.in(null != this.getSortIn() && this.getSortIn().size() > 0,T::getSort, this.getSortIn());
          builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0,T::getSort, this.getSortNotIn());
          builder.gt(this.getSortGt() != null,T::getSort, this.getSortGt());
          builder.ge(this.getSortGte() != null,T::getSort, this.getSortGte());         
          builder.lt(this.getSortLt() != null,T::getSort, this.getSortLt());         
          builder.le(this.getSortLte() != null,T::getSort, this.getSortLte());

  
        };
    }

    public <T extends SysMenu> Consumer<LambdaQueryWrapper<T>> conditionParentId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getParentId()),SysMenu::getParentId, StrUtil.trim(this.getParentId()));
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

    /**
     * 构建查询条件
     */
    public <T extends SysMenu> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.<T>lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionOwnerAppId().accept(builder);
        this.<T>conditionNodeKey().accept(builder);
        this.<T>conditionNodePath().accept(builder);
        this.<T>conditionKey().accept(builder);
        this.<T>conditionName().accept(builder);
        this.<T>conditionStatus().accept(builder);
        this.<T>conditionDesc().accept(builder);
        this.<T>conditionDefaultGrant().accept(builder);
        this.<T>conditionVisible().accept(builder);
        this.<T>conditionMenuType().accept(builder);
        this.<T>conditionPath().accept(builder);
        this.<T>conditionComponent().accept(builder);
        this.<T>conditionIcon().accept(builder);
        this.<T>conditionTag().accept(builder);
        this.<T>conditionClasses().accept(builder);
        this.<T>conditionSort().accept(builder);
        this.<T>conditionParentId().accept(builder);
        return builder;
    }

}