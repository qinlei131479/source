package com.course.springtest.factory;

/**
 * spring容器工厂大佬：创建bean
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:28
 */
public interface BeanFactory {

	Object getBean(String beanName);
}
