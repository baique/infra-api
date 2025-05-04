package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;

/**
 * 应用管理 sys_app
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_app_")
public class SysApp extends BizBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 应用标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 应用名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 主页地址
     */
    @TableField(value = "main_page_path_")
    private String mainPagePath;
    /**
     * 应用密钥
     */
    @TableField(value = "secret_")
    private String secret;
    /**
     * 应用状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 应用描述
     */
    @TableField(value = "desc_")
    private String desc;
    /**
     * 排序编号
     */
    @TableField(value = "sort_")
    private Integer sort;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysApp entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setMainPagePath(entity.getMainPagePath());
        this.setSecret(entity.getSecret());
        this.setStatus(entity.getStatus());
        this.setDesc(entity.getDesc());
        this.setSort(entity.getSort());
    }
}