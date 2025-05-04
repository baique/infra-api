package tech.hljzj.infrastructure.vo.SysActiviti.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysActiviti;

/**
 * 流程管理 sys_activiti_ 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysActivitiUpdateBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    private String id;
    /**
     * 模型标识
     */
    private String key;
    /**
     * 模型名称
     */
    private String name;
    /**
     * 模型状态
     */
    private String status;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 模型描述信息
     */
    private String desc;

    /**
     * 转换为实际的数据实体
     */
    public SysActiviti toDto(){
        SysActiviti dto = new SysActiviti();
          
        dto.setId(this.getId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setStatus(this.getStatus());
        dto.setSort(this.getSort());
        dto.setDesc(this.getDesc());

        return dto;
    }

}