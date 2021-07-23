package com.course.common.redission.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

import com.course.common.redission.enums.LockModel;

/**
 * 分布式锁注解
 * 
 * @author qinlei
 * @date 2021/7/21 下午10:52
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

	/**
	 * 如果keys有多个,如果不设置,则使用 联锁
	 *
	 * @return
	 */
	String[] keys() default {};

	/**
	 * 锁的模式:如果不设置,自动模式,当参数只有一个.使用 REENTRANT 参数多个 MULTIPLE
	 */
	LockModel lockModel() default LockModel.AUTO;

	/**
	 * key的静态常量:当key的spel的值是LIST,数组时使用+号连接将会被spel认为这个变量是个字符串,只能产生一把锁,达不到我们的目的,<br/>
	 * 而我们如果又需要一个常量的话.这个参数将会在拼接在每个元素的后面
	 * 
	 * @return
	 */
	String keyConstant() default "";

	/**
	 * 锁超时时间,默认30秒
	 * 
	 * @return
	 */
	long leaseTime() default 10;

	/**
	 * 等待加锁超时时间,默认3秒
	 * 
	 * @return
	 */
	long waitTime() default 3;

	/**
	 * 执行时间单位
	 * 
	 * @return
	 */
	TimeUnit unit() default TimeUnit.SECONDS;
}
