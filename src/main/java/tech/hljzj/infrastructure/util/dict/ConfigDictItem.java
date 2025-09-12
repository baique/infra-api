package tech.hljzj.infrastructure.util.dict;

import java.lang.annotation.*;

//@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConfigDictItem {
    /**
     * 字典绑定位置 从0开始
     * @return 0
     */
    int index();

    /**
     * 字段名
     * @return 字段名
     */
    String field();

    /**
     * 使用字典标识
     * @return 字典标识
     */
    String dict();

    Class<? extends DictLoader> custom() default DictLoader.class;

    /**
     * 默认值，当导出或者导入值为空时，默认使用
     * @return 默认值
     */
    String defaultValue() default "";
}
