package tech.hljzj.activiti.pojo.node;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessProp {
    /**
     * 用作判定条件的标识
     */
    private String key;
    /**
     * 该表单项的名称
     */
    private String name;
    /**
     * 组件
     */
    private String component;
    /**
     * 传入参数
     */
    @Setter(AccessLevel.NONE)
    private JSONObject props = new JSONObject();
    private boolean visible;
    private boolean readonly;
    private boolean required;
    private boolean canUpdate;

    public static ProcessProp create(String key) {
        return new ProcessProp().setKey(key);
    }

    @JsonAnySetter
    public ProcessProp addProp(String key, Object value) {
        props.putByPath(key, value);
        return this;
    }

    public Object prop(String key) {
        return props.get(key);
    }
}
