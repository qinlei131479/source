package com.course.mvc.mapping;

import javax.servlet.http.HttpServletRequest;

import com.course.mvc.mapping.face.HandlerMapping;
import com.course.springtest.aware.BeanFactoryAware;
import com.course.springtest.factory.BeanFactory;
import com.course.springtest.factory.support.DefaultListableBeanFactory;

/**
 * 通过bean标签的；props属性去查找处理器映射器
 * 
 * @author qinlei
 * @date 2021/6/8 下午10:06
 */
public class SimpleUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;
	/**
	 * 初始化执行方法
	 */
	public void init() {

	}

	@Override
	public Object getHandler(HttpServletRequest request) {
		return null;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}
}
