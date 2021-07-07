package com.course.mvc.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author qinlei
 * @date 2021/6/9 下午2:37
 */
@Retention(RUNTIME)
@Target({ METHOD, TYPE })
public @interface RequestMapping {

    String value() default "";
}
