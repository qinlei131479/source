package com.course.springtest.factory.support;

import com.course.springtest.factory.BeanFactory;
import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.registry.support.DefaultSingletonBeanRegistry;

/**
 * @author qinlei
 * @date 2021/6/4 下午5:55
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String beanName) {
		// 先从一级缓存中获取单例Bean的实例
		Object singletonObject = getSingleton(beanName);

		if (singletonObject != null) {
			return singletonObject;
		}
		// 懒汉式，等你getBean的时候，并且singletonObjects没有该实例的时候，才去创建该实例
		// 当缓存中没有找到该Bean实例，则需要创建Bean，然后将该Bean放入一级缓存中
		// 要创建Bean，需要知道该Bean的信息（这个信息是配置到XML中的）

		// 根据beanName去beanDefinitions获取对应的Bean信息
		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		if (beanDefinition == null) {
			return null;
		}
		// 根据Bean的信息，来判断该bean是单例bean还是多例（原型）bean
		if (beanDefinition.isSingleton()) {
			// 根据Bean的信息去创建Bean的对象
			singletonObject = createBean(beanDefinition);
			// 将Bean的对象，存入到singletonObjects
			addSingleton(beanName, singletonObject);
		} else if (beanDefinition.isPrototype()) {
			// 根据Bean的信息去创建Bean的对象
			singletonObject = createBean(beanDefinition);
		} else {
			// TODO 。。。
		}

		return singletonObject;
	}

	/**
	 * 钩子函数，获取BeanDefinition
	 * 
	 * @param beanName
	 * @return
	 */
	protected abstract BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 钩子函数，子类去创建
	 * 
	 * @param beanDefinition
	 * @return
	 */
	protected abstract Object createBean(BeanDefinition beanDefinition);
}
