package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.service.entity.DictData;
import tech.hljzj.framework.service.sort.ISort;

import java.io.Serial;

/**
 * 字典数据 sys_dict_data
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dict_data_", autoResultMap = true)

public class SysDictData extends DictData implements ISort {
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
     * 所属字典类型标识
     */
    @TableField(value = "owner_type_id_")
    private String ownerTypeId;
    /**
     * 是否锁定
     */
    @TableField(value = "locked_")
    private String locked;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysDictData entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setValue(entity.getValue());
        this.setListClass(entity.getListClass());
        this.setSelectable(entity.getSelectable());
        this.setStatus(entity.getStatus());
        this.setHelpMessage(entity.getHelpMessage());
        this.setDesc(entity.getDesc());
        this.setLocked(entity.getLocked());
        this.setSort(entity.getSort());
        this.setAttribution(entity.getAttribution());
    }
}