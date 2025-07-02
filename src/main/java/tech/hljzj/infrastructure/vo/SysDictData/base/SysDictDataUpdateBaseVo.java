package tech.hljzj.infrastructure.vo.SysDictData.base;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDictData;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据 sys_dict_data 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysDictDataUpdateBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * bigint
     */
    @NotBlank
    private String id;
    /**
     * 字典标识
     */
    @NotBlank
    private String key;
    /**
     * 字典名称
     */
    @NotBlank
    private String name;
    /**
     * 字典项值
     */
    private String value;
    /**
     * 数据样式
     */
    private String listClass;
    /**
     * 是否可选
     */
    private String selectable;
    /**
     * 字典状态
     */
    @NotBlank
    private String status;
    /**
     * 选项说明
     */
    private String helpMessage;
    /**
     * 字典描述
     */
    private String desc;
    /**
     * 是否锁定
     */
    private String locked;
    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 转换为实际的数据实体
     */
    public SysDictData toDto(){
        SysDictData dto = new SysDictData();
          
        dto.setId(this.getId());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setValue(this.getValue());
        dto.setListClass(this.getListClass());
        dto.setSelectable(this.getSelectable());
        dto.setStatus(this.getStatus());
        dto.setHelpMessage(this.getHelpMessage());
        dto.setDesc(this.getDesc());
        dto.setLocked(this.getLocked());
        dto.setSort(this.getSort());

        return dto;
    }

}