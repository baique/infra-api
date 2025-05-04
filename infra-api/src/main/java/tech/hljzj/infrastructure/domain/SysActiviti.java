package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import java.lang.*;
import java.util.Date;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;

/**
 * 流程管理 sys_activiti_
 * DTO实体
 * 
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_activiti_")
public class SysActiviti extends BizBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id_")
    private String id;
    /**
     * 模型标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 模型名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 模型状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 排序编号
     */
    @TableField(value = "sort_")
    private Integer sort;
    /**
     * 模型描述信息
     */
    @TableField(value = "desc_")
    private String desc;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysActiviti entity){
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setStatus(entity.getStatus());
        this.setSort(entity.getSort());
        this.setDesc(entity.getDesc());
    }
}