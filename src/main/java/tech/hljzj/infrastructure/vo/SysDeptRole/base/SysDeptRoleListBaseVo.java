package tech.hljzj.infrastructure.vo.SysDeptRole.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysDeptRole;
import java.lang.*;
import java.util.Date;


/**
 * 部门关联角色 sys_dept_role_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptRoleListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;

    public <T extends SysDeptRoleListBaseVo> T fromDto(SysDeptRole dto){
        this.setId(dto.getId());
        //noinspection unchecked
        return (T) this;
    }

    public SysDeptRole toDto(){
        SysDeptRole dto = new SysDeptRole();
        dto.setId(this.getId());
        return dto;
    }
}