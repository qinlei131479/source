package com.course.common.core.annotation;

import java.lang.annotation.*;

/**
 * @author qinlei
 * @date 2021/6/24 下午5:21
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpress {

    String enableField();

    String[] enableValues();

    /**
     * enable生效的group数组
     *
     * @return
     */
    Class<?>[] groups() default {};
}
