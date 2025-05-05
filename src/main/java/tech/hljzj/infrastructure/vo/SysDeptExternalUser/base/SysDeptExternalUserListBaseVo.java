package tech.hljzj.infrastructure.vo.SysDeptExternalUser.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysDeptExternalUser;
import java.lang.*;


/**
 * 外部编入人员 sys_dept_external_user_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptExternalUserListBaseVo implements Serializable {
    @Serial
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
     * 用户标识
     */
    @ExcelProperty(value = "用户标识")
    private String userId;
    /**
     * 用户身份
     */
    @ExcelProperty(value = "用户身份")
    private String identity;
    /**
     * 备注信息
     */
    @ExcelProperty(value = "备注信息")
    private String remarks;

    public <T extends SysDeptExternalUserListBaseVo> T fromDto(SysDeptExternalUser dto){
        this.setId(dto.getId());
        this.setDeptId(dto.getDeptId());
        this.setUserId(dto.getUserId());
        this.setIdentity(dto.getIdentity());
        this.setRemarks(dto.getRemarks());
        //noinspection unchecked
        return (T) this;
    }

    public SysDeptExternalUser toDto(){
        SysDeptExternalUser dto = new SysDeptExternalUser();
        dto.setId(this.getId());
        dto.setDeptId(this.getDeptId());
        dto.setUserId(this.getUserId());
        dto.setIdentity(this.getIdentity());
        dto.setRemarks(this.getRemarks());
        return dto;
    }
}