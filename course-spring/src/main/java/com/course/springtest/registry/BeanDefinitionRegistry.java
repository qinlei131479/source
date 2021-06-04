package com.course.springtest.registry;

import java.util.List;

import com.course.springtest.ioc.BeanDefinition;

/**
 * 存储的BeanDefinition集合进行操作的功能接口
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:38
 */
public interface BeanDefinitionRegistry {

	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

	BeanDefinition getBeanDefinition(String beanName);

	List<BeanDefinition> getBeanDefinitions();
}
