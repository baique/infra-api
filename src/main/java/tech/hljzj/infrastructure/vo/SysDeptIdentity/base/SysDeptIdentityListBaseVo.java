package tech.hljzj.infrastructure.vo.SysDeptIdentity.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDeptIdentity;

import java.io.Serializable;


/**
 * 岗位管理 sys_dept_identity_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptIdentityListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 部门标识
     */
    @ExcelProperty(value = "部门标识")
    private String deptId;
    /**
     * 岗位标识
     */
    @ExcelProperty(value = "岗位标识")
    private String key;
    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位名称")
    private String name;
    /**
     * 岗位顺序
     */
    @ExcelProperty(value = "岗位顺序")
    private Integer sort;
    /**
     * 岗位描述
     */
    @ExcelProperty(value = "岗位描述")
    private String desc;

    public <T extends SysDeptIdentityListBaseVo> T fromDto(SysDeptIdentity dto){
        this.setId(dto.getId());
        this.setDeptId(dto.getDeptId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setSort(dto.getSort());
        this.setDesc(dto.getDesc());
        //noinspection unchecked
        return (T) this;
    }

    public SysDeptIdentity toDto(){
        SysDeptIdentity dto = new SysDeptIdentity();
        dto.setId(this.getId());
        dto.setDeptId(this.getDeptId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setDesc(this.getDesc());
        return dto;
    }
}