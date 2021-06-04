package com.course.springtest.registry;

/**
 * 单例bean注册
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:49
 */
public interface SingletonBeanRegistry {

	Object getSingleton(String beanName);

	void addSingleton(String beanName, Object bean);
}
