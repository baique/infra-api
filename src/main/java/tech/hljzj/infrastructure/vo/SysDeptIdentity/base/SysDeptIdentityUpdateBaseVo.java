package tech.hljzj.infrastructure.vo.SysDeptIdentity.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

import tech.hljzj.infrastructure.domain.SysDeptIdentity;

/**
 * 岗位管理 sys_dept_identity_ 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptIdentityUpdateBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    private String id;
    /**
     * 部门标识
     */
    private String deptId;
    /**
     * 岗位标识
     */
    private String key;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位顺序
     */
    private Integer sort;
    /**
     * 岗位描述
     */
    private String desc;

    /**
     * 转换为实际的数据实体
     */
    public SysDeptIdentity toDto(){
        SysDeptIdentity dto = new SysDeptIdentity();
          
        dto.setId(this.getId());
        dto.setDeptId(this.getDeptId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setSort(this.getSort());
        dto.setDesc(this.getDesc());

        return dto;
    }

}