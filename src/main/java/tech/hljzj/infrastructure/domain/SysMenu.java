package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;
import tech.hljzj.framework.service.sort.ISort;

import java.io.Serial;

/**
 * 菜单管理 sys_menu
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_menu_")
public class SysMenu extends BizBaseEntity implements ISort {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 所属应用标识
     */
    @TableField(value = "owner_app_id_")
    private String ownerAppId;
    /**
     * 节点标识
     */
    @TableField(value = "node_key_")
    private String nodeKey;
    /**
     * 节点路径
     */
    @TableField(value = "node_path_")
    private String nodePath;
    /**
     * 菜单标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 菜单名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 菜单状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 菜单描述
     */
    @TableField(value = "desc_")
    private String desc;
    /**
     * 默认授予登录用户
     */
    @TableField(value = "default_grant_")
    private String defaultGrant;
    /**
     * 显示状态
     */
    @TableField(value = "visible_")
    private String visible;
    /**
     * 菜单类型
     */
    @TableField(value = "menu_type_")
    private String menuType;
    /**
     * 访问路径
     */
    @TableField(value = "path_")
    private String path;
    /**
     * 引用组件
     */
    @TableField(value = "component_")
    private String component;
    /**
     * 菜单图标
     */
    @TableField(value = "icon_")
    private String icon;
    /**
     * 标签
     */
    @TableField(value = "tag_")
    private String tag;
    /**
     * 类型
     */
    @TableField(value = "classes_")
    private String classes;
    /**
     * 排序编号
     */
    @TableField(value = "sort_")
    private Integer sort;
    /**
     * 上级权限标识
     */
    @TableField(value = "parent_id_")
    private String parentId;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysMenu entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setStatus(entity.getStatus());
        this.setDesc(entity.getDesc());
        this.setDefaultGrant(entity.getDefaultGrant());
        this.setVisible(entity.getVisible());
        this.setMenuType(entity.getMenuType());
        this.setPath(entity.getPath());
        this.setComponent(entity.getComponent());
        this.setIcon(entity.getIcon());
        this.setTag(entity.getTag());
        this.setClasses(entity.getClasses());
        this.setSort(entity.getSort());
        this.setParentId(entity.getParentId());
    }
}