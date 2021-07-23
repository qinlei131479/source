package com.course.common.redission.annotation;

import java.lang.annotation.*;

/**
 * MQ发送消息注解
 * 
 * @author qinlei
 * @date 2021/7/22 下午9:39
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MqPublish {

	/**
	 * topic name
	 * 
	 * @return
	 */
	String name();
}
