package com.course.springtest.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.course.common.utils.ReflectUtil;
import com.course.springtest.factory.ListableBeanFactory;
import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.registry.BeanDefinitionRegistry;

/**
 * @author qinlei
 * @date 2021/6/4 下午5:21
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements BeanDefinitionRegistry, ListableBeanFactory {

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
		// List<BeanDefinition> beanDefinitionList = new ArrayList<>();
		// for (BeanDefinition beanDefinition : beanDefinitions.values()) {
		// beanDefinitionList.add(beanDefinition);
		// }
		// return beanDefinitionList;
		return new ArrayList<>(this.beanDefinitions.values());
	}

	@Override
	public <T> List<T> getBeansByType(Class<T> clazz) {
		List<T> results = new ArrayList<T>();
		for (BeanDefinition bd : beanDefinitions.values()) {
			Class<?> type = ReflectUtil.resolveType(bd.getClazzName());
			if (clazz.isAssignableFrom(type)) {
				Object bean = getBean(bd.getBeanName());
				results.add((T) bean);
			}
		}
		return results;
	}

}
