package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;
import tech.hljzj.framework.service.sort.ISort;



/**
 * 角色管理 sys_role
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_role_")
public class SysRole extends BizBaseEntity implements ISort {
    
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
     * 角色标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 角色名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 角色状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 角色描述
     */
    @TableField(value = "desc_")
    private String desc;
    /**
     * 默认授予登录用户
     */
    @TableField(value = "default_grant_")
    private String defaultGrant;
    /**
     * 优先级（多个角色存在冲突时以哪个角色为主）
     */
    @TableField(value = "priority_")
    private Integer priority;
    /**
     * 主页地址
     */
    @TableField(value = "main_page_path_")
    private String mainPagePath;
    /**
     * 排序编号
     */
    @TableField(value = "sort_")
    private Integer sort;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysRole entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setStatus(entity.getStatus());
        this.setDesc(entity.getDesc());
        this.setDefaultGrant(entity.getDefaultGrant());
        this.setPriority(entity.getPriority());
        this.setMainPagePath(entity.getMainPagePath());
        this.setSort(entity.getSort());
    }
}