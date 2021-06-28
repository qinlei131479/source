package com.course.common.valid;

import java.lang.annotation.*;

/**
 * @author qinlei
 * @date 2021/6/28 下午3:44
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqFieldExpress {

	/**
	 * 类型：文本
	 */
	String TYPE_INPUT = "input";
	/**
	 * 类型：多行文本
	 */
	String TYPE_TEXTAREA = "textarea";
	/**
	 * 类型：单选
	 */
	String TYPE_RADIO = "radio";
	/**
	 * 类型：下拉
	 */
	String TYPE_SELECT = "select";

	String label();

	String description() default "";

	int width() default 0;

	int rows() default 1;

	String type() default TYPE_INPUT;

	Class<?>[] enumClazzs() default {};
}
