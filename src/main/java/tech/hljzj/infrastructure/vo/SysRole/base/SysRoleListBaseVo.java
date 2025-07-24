package tech.hljzj.infrastructure.vo.SysRole.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysRole;


/**
 * 角色管理 sys_role 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysRoleListBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    @ExcelProperty(value = "bigint")
    private String id;
    /**
     * 所属应用标识
     */
    @ExcelProperty(value = "所属应用标识")
    private String ownerAppId;
    /**
     * 角色标识
     */
    @ExcelProperty(value = "角色标识")
    private String key;
    /**
     * 角色名称
     */
    @ExcelProperty(value = "角色名称")
    private String name;
    /**
     * 角色状态
     */
    @ExcelProperty(value = "角色状态")
    private String status;
    /**
     * 角色描述
     */
    @ExcelProperty(value = "角色描述")
    private String desc;
    /**
     * 默认授予登录用户
     */
    @ExcelProperty(value = "默认授予登录用户")
    private String defaultGrant;
    /**
     * 优先级（多个角色存在冲突时以哪个角色为主）
     */
    @ExcelProperty(value = "优先级（多个角色存在冲突时以哪个角色为主）")
    private Integer priority;
    /**
     * 主页地址
     */
    @ExcelProperty(value = "主页地址")
    private String mainPagePath;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;

    public <T extends SysRoleListBaseVo> T fromDto(SysRole dto){
        this.setId(dto.getId());
        this.setOwnerAppId(dto.getOwnerAppId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setStatus(dto.getStatus());
        this.setDesc(dto.getDesc());
        this.setDefaultGrant(dto.getDefaultGrant());
        this.setPriority(dto.getPriority());
        this.setMainPagePath(dto.getMainPagePath());
        this.setSort(dto.getSort());
        //noinspection unchecked
        return (T) this;
    }

    public SysRole toDto(){
        SysRole dto = new SysRole();
        dto.setId(this.getId());
        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setDefaultGrant(this.getDefaultGrant());
        dto.setPriority(this.getPriority());
        dto.setSort(this.getSort());
        return dto;
    }
}