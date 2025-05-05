package tech.hljzj.infrastructure.vo.SysDeptExternalUser.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.*;

import tech.hljzj.infrastructure.domain.SysDeptExternalUser;

/**
 * 外部编入人员 sys_dept_external_user_ 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptExternalUserNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 部门标识
     */
    private String deptId;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 用户身份
     */
    private String identity;
    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 转换为实际的数据实体
     */
    public SysDeptExternalUser toDto(){
        SysDeptExternalUser dto = new SysDeptExternalUser();
          
        dto.setDeptId(this.getDeptId());
        dto.setUserId(this.getUserId());
        dto.setIdentity(this.getIdentity());
        dto.setRemarks(this.getRemarks());

        return dto;
    }
}