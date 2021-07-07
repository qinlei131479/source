package com.course.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author qinlei
 * @date 2021/6/9 下午2:34
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    String value() default "";
}
