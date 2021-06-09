package com.course.mvc.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.course.mvc.mapping.face.HandlerMapping;
import com.course.springtest.aware.BeanFactoryAware;
import com.course.springtest.factory.BeanFactory;
import com.course.springtest.factory.support.DefaultListableBeanFactory;
import com.course.springtest.ioc.BeanDefinition;

/**
 * 通过bean标签的name属性去查找处理器映射器
 * 
 * @author qinlei
 * @date 2021/6/8 下午10:04
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;
	/**
	 * key：请求URI value：处理器类
	 */
	private Map<String, Object> urlHandlerMap = new HashMap<String, Object>();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	public void init() {
		List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
		for (BeanDefinition beanDefinition : beanDefinitions) {
			String beanName = beanDefinition.getBeanName();
			if (beanName.startsWith("/")) {
				urlHandlerMap.put(beanName, beanFactory.getBean(beanName));
			}
		}

		// TODO
		// urlHandlerMap.put("/queryUser", new QueryUserHandler());
		// urlHandlerMap.put("/addUser", new AddUserHandler());
	}

	@Override
	public Object getHandler(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return urlHandlerMap.get(uri);
	}
}
