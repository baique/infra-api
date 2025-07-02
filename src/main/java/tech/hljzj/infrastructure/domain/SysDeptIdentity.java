package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.service.sort.ISort;

import java.io.Serializable;

/**
 * 岗位管理 sys_dept_identity_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dept_identity_")
public class SysDeptIdentity implements ISort, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id_")
    private String id;
    /**
     * 部门标识
     */
    @TableField(value = "dept_id_")
    private String deptId;
    /**
     * 岗位标识
     */
    @TableField(value = "key_")
    private String key;
    /**
     * 岗位名称
     */
    @TableField(value = "name_")
    private String name;
    /**
     * 岗位顺序
     */
    @TableField(value = "sort_")
    private Integer sort;
    /**
     * 岗位描述
     */
    @TableField(value = "desc_")
    private String desc;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysDeptIdentity entity) {
        this.setDeptId(entity.getDeptId());
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setSort(entity.getSort());
        this.setDesc(entity.getDesc());
    }
}