package tech.hljzj.infrastructure.vo.SysRole.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysRole;

/**
 * 角色管理 sys_role 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysRoleUpdateBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    private String id;
    /**
     * 角色标识
     */
    private String key;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色状态
     */
    private String status;
    /**
     * 角色描述
     */
    private String desc;
    /**
     * 默认授予登录用户
     */
    private String defaultGrant;
    /**
     * 优先级（多个角色存在冲突时以哪个角色为主）
     */
    private Integer priority;
    /**
     * 主页地址
     */
    private String mainPagePath;
    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 转换为实际的数据实体
     */
    public SysRole toDto(){
        SysRole dto = new SysRole();
          
        dto.setId(this.getId());
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