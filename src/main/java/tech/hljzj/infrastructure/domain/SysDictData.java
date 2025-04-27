package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.service.entity.DictData;
import tech.hljzj.framework.service.sort.ISort;

/**
 * 字典数据 sys_dict_data
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dict_data_")
public class SysDictData extends DictData implements ISort {
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
     * 从新的实体中更新属性
     */
    public void updateForm(SysDictData entity) {
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setValue(entity.getValue());
        this.setListClass(entity.getListClass());
        this.setStatus(entity.getStatus());
        this.setDesc(entity.getDesc());
        this.setSort(entity.getSort());
    }
}