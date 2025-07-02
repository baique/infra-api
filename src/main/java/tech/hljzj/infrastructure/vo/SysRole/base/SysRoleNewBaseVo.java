package tech.hljzj.infrastructure.vo.SysRole.base;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.code.AppConst;
import tech.hljzj.infrastructure.domain.SysRole;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 角色管理 sys_role 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysRoleNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 所属应用标识
     */
    @NotBlank
    private String ownerAppId;
    /**
     * 角色标识
     */
    @NotBlank
    private String key;
    /**
     * 角色名称
     */
    @NotBlank
    private String name;
    /**
     * 角色状态
     */
    @NotBlank
    private String status;
    /**
     * 角色描述
     */
    private String desc;
    /**
     * 默认授予登录用户
     */
    private String defaultGrant = AppConst.NOT;
    /**
     * 优先级（多个角色存在冲突时以哪个角色为主）
     */
    private Integer priority = 0;
    /**
     * 主页地址
     */
    private String mainPagePath;
    /**
     * 排序编号
     */
    private Integer sort = 0;

    /**
     * 转换为实际的数据实体
     */
    public SysRole toDto() {
        SysRole dto = new SysRole();

        dto.setOwnerAppId(this.getOwnerAppId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setDefaultGrant(this.getDefaultGrant());
        dto.setPriority(this.getPriority());
        dto.setMainPagePath(this.getMainPagePath());
        dto.setSort(this.getSort());

        return dto;
    }
}