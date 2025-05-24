package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;
import tech.hljzj.framework.service.entity.ConfigData;
import tech.hljzj.framework.service.sort.ISort;

import java.io.Serial;

/**
 * 系统配置 sys_config
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_config_")
public class SysConfig extends ConfigData implements ISort {
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
     * 从新的实体中更新属性
     */
    public void updateForm(SysConfig entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setValue(entity.getValue());
        this.setStatus(entity.getStatus());
        this.setDesc(entity.getDesc());
        this.setSort(entity.getSort());
        this.setLocked(entity.getLocked());
    }
}