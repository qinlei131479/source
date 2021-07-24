package com.course.common.redission.annotation;

import java.lang.annotation.*;

import com.course.common.redission.enums.MqModel;

/**
 * MQ监听消息
 * 
 * @author qinlei
 * @date 2021/7/22 下午9:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface MqListener {
	/**
	 * topic name
	 * 
	 * @return
	 */
	String name() default "topic";

	/**
	 * 匹配模式 <br />
	 * PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic <br />
	 * PATTERN模糊匹配 如: name="myTopic.*" 那么发送者的topic name 可以是 myTopic.name1
	 * mTopic.name2.尾缀不限定
	 * 
	 * @return
	 */
	MqModel model() default MqModel.PRECISE;
}
