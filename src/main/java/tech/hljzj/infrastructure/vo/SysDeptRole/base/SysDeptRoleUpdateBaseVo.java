package tech.hljzj.infrastructure.vo.SysDeptRole.base;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDeptRole;

import java.io.Serializable;

/**
 * 部门关联角色 sys_dept_role_ 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptRoleUpdateBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    private String id;

    /**
     * 转换为实际的数据实体
     */
    public SysDeptRole toDto(){
        SysDeptRole dto = new SysDeptRole();
          
        dto.setId(this.getId());

        return dto;
    }

}