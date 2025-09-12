package tech.hljzj.infrastructure.util.dict;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DictStore {
    private String defaultValue;
    private Map<String, String> dictMap;
}
