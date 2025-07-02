package tech.hljzj.infrastructure.vo.SysDeptRole.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysDeptRole;

/**
 * 部门关联角色 sys_dept_role_ 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptRoleNewBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 转换为实际的数据实体
     */
    public SysDeptRole toDto(){
        SysDeptRole dto = new SysDeptRole();
          

        return dto;
    }
}