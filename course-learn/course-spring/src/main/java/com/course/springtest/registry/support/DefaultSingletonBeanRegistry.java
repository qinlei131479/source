package com.course.springtest.registry.support;

import java.util.HashMap;
import java.util.Map;

import com.course.springtest.registry.SingletonBeanRegistry;

/**
 * 单例bean默认实现
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:50
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	/**
	 * K:BeanName V:Bean实例对象
	 */
	private Map<String, Object> singletonObjects = new HashMap<String, Object>();

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	@Override
	public void addSingleton(String beanName, Object bean) {
		singletonObjects.put(beanName, bean);
	}
}
