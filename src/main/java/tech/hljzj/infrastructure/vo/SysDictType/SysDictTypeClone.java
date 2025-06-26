package tech.hljzj.infrastructure.vo.SysDictType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SysDictTypeClone {
    /**
     * 字典标识
     */
    @NotBlank(message = "字典标识不允许为空")
    private String dictId;
    /**
     * 目标应用标识
     */
    @NotBlank(message = "应用标识不允许为空")
    private String cloneToAppId;
}
