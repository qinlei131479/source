package com.course.springtest.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.registry.BeanDefinitionRegistry;

/**
 * @author qinlei
 * @date 2021/6/4 下午5:21
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

	/**
	 * K:BeanName V:BeanDefinition对象
	 */
	private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitions.put(beanName, beanDefinition);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return this.beanDefinitions.get(beanName);
	}

	@Override
	public List<BeanDefinition> getBeanDefinitions() {
		return new ArrayList<>(this.beanDefinitions.values());
	}
}
