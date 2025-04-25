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
 * 字典组 sys_dict_type
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dict_type_")
public class SysDictType extends BizBaseEntity implements ISort {
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
     * 字典类型标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 字典类型名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 字典类型状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 值长度限制
     */
    @TableField(value = "max_length_")
    private Integer maxLength;
    /**
     * 字典说明
     */
    @TableField(value = "help_message_")
    private String helpMessage;
    /**
     * 字典类型描述
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
    public void updateForm(SysDictType entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setStatus(entity.getStatus());
        this.setMaxLength(entity.getMaxLength());
        this.setHelpMessage(entity.getHelpMessage());
        this.setDesc(entity.getDesc());
        this.setSort(entity.getSort());
    }
}