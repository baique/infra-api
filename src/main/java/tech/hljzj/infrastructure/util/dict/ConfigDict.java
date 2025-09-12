package tech.hljzj.infrastructure.util.dict;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConfigDict {
    ConfigDictItem[] value();
}
