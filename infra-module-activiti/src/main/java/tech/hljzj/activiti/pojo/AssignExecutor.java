package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AssignExecutor {
    private String label;
    private String value;

    public static AssignExecutor of(String label, String value) {
        return new AssignExecutor().setLabel(label).setValue(value);
    }
}
