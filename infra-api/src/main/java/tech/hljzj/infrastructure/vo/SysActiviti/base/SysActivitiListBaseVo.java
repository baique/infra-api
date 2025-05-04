package tech.hljzj.infrastructure.vo.SysActiviti.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysActiviti;
import java.lang.*;
import java.util.Date;


/**
 * 流程管理 sys_activiti_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysActivitiListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 模型标识
     */
    @ExcelProperty(value = "模型标识")
    private String key;
    /**
     * 模型名称
     */
    @ExcelProperty(value = "模型名称")
    private String name;
    /**
     * 模型状态
     */
    @ExcelProperty(value = "模型状态")
    private String status;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;
    /**
     * 模型描述信息
     */
    @ExcelProperty(value = "模型描述信息")
    private String desc;

    public <T extends SysActivitiListBaseVo> T fromDto(SysActiviti dto){
        this.setId(dto.getId());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setStatus(dto.getStatus());
        this.setSort(dto.getSort());
        this.setDesc(dto.getDesc());
        //noinspection unchecked
        return (T) this;
    }

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