package tech.hljzj.infrastructure.vo.SysMenu.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

import tech.hljzj.infrastructure.domain.SysMenu;

import javax.validation.constraints.NotBlank;

/**
 * 菜单管理 sys_menu 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysMenuUpdateBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @NotBlank
    private String id;
    /**
     * 菜单标识
     */
    @NotBlank
    private String key;
    /**
     * 菜单名称
     */
    @NotBlank
    private String name;
    /**
     * 菜单状态
     */
    @NotBlank
    private String status;
    /**
     * 菜单描述
     */
    private String desc;
    /**
     * 默认授予登录用户
     */
    private String defaultGrant;
    /**
     * 显示状态
     */
    @NotBlank
    private String visible;
    /**
     * 菜单类型
     */
    @NotBlank
    private String menuType;
    /**
     * 访问路径
     */
    private String path;
    /**
     * 引用组件
     */
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 标签
     */
    private String tag;
    /**
     * 类型
     */
    private String classes;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 上级权限标识
     */
    @NotBlank
    private String parentId;

    /**
     * 转换为实际的数据实体
     */
    public SysMenu toDto(){
        SysMenu dto = new SysMenu();

        dto.setId(this.getId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setDefaultGrant(this.getDefaultGrant());
        dto.setVisible(this.getVisible());
        dto.setMenuType(this.getMenuType());
        dto.setPath(this.getPath());
        dto.setComponent(this.getComponent());
        dto.setIcon(this.getIcon());
        dto.setTag(this.getTag());
        dto.setClasses(this.getClasses());
        dto.setSort(this.getSort());
        dto.setParentId(this.getParentId());

        return dto;
    }

}