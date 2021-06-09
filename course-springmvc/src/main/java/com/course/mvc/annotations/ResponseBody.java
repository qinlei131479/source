package com.course.mvc.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author qinlei
 * @date 2021/6/9 下午2:34
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseBody {

	String value() default "";
}
