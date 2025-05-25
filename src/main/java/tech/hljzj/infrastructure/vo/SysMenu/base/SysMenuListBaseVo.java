package tech.hljzj.infrastructure.vo.SysMenu.base;

import com.alibaba.excel.annotation.ExcelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.util.excel.dict.DictConvertHandle;
import tech.hljzj.framework.util.excel.dict.UseDict;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysMenu;

import java.io.Serial;
import java.io.Serializable;


/**
 * 菜单管理 sys_menu
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysMenuListBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    private String id;
    /**
     * 所属应用标识
     */
    private String ownerAppId;
    
    /**
     * 菜单标识
     */
    @ExcelProperty(value = "菜单标识")
    @NotBlank(message = "菜单标识不可为空")
    private String key;
    /**
     * 菜单名称
     */
    @ExcelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不可为空")
    private String name;
    /**
     * 菜单状态
     */
    @ExcelProperty(value = "菜单状态")
    private String status;
    /**
     * 菜单描述
     */
    @ExcelProperty(value = "菜单描述")
    private String desc;
    /**
     * 默认授予登录用户
     */
    private String defaultGrant;
    /**
     * 显示状态
     */
    @ExcelProperty(value = "是否显示", converter = DictConvertHandle.class)
    @UseDict("yes_no")
    private String visible = AppConst.YES;
    /**
     * 菜单类型
     */
    @ExcelProperty(value = "菜单类型", converter = DictConvertHandle.class)
    @UseDict("menu_type")
    private String menuType;
    /**
     * 访问路径
     */
    @ExcelProperty(value = "访问路径")
    private String path;
    /**
     * 引用组件
     */
    @ExcelProperty(value = "引用组件")
    private String component;
    /**
     * 菜单图标
     */
//    @ExcelProperty(value = "菜单图标")
    private String icon;
    /**
     * 标签
     */
    @ExcelProperty(value = "标签")
    private String tag;
    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String classes;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 上级菜单标识
     */
//    @ExcelProperty(value = "上级菜单标识")
    private String parentId;

    public <T extends SysMenuListBaseVo> T fromDto(SysMenu dto) {
        this.setId(dto.getId());
        this.setOwnerAppId(dto.getOwnerAppId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setStatus(dto.getStatus());
        this.setDesc(dto.getDesc());
        this.setDefaultGrant(dto.getDefaultGrant());
        this.setVisible(dto.getVisible());
        this.setMenuType(dto.getMenuType());
        this.setPath(dto.getPath());
        this.setComponent(dto.getComponent());
        this.setIcon(dto.getIcon());
        this.setTag(dto.getTag());
        this.setClasses(dto.getClasses());
        this.setSort(dto.getSort());
        this.setParentId(dto.getParentId());
        //noinspection unchecked
        return (T) this;
    }

    public SysMenu toDto() {
        SysMenu dto = new SysMenu();
        dto.setId(this.getId());
        dto.setOwnerAppId(this.getOwnerAppId());
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