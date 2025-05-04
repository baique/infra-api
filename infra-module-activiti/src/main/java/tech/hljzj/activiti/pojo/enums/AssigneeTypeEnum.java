package tech.hljzj.activiti.pojo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssigneeTypeEnum {
    USER("user", "用户"),
    ROLE("role", "角色");

    @JsonValue
    private final String type;
    private final String description;
}
