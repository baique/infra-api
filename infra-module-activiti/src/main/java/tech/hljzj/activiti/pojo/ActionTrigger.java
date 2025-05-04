package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ActionTrigger {
    private String key;
    private String name;

    public static ActionTrigger of(String name, String key) {
        return new ActionTrigger().setKey(key).setName(name);
    }
}
