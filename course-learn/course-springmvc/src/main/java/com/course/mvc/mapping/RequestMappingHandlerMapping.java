package com.course.mvc.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.course.mvc.annotations.Controller;
import com.course.mvc.annotations.RequestMapping;
import com.course.mvc.handler.HandlerMethod;
import com.course.mvc.mapping.face.HandlerMapping;
import com.course.springtest.aware.BeanFactoryAware;
import com.course.springtest.factory.BeanFactory;
import com.course.springtest.factory.support.DefaultListableBeanFactory;
import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.utils.ReflectUtil;

/**
 * 该处理器映射器主要映射的处理器是@Controller类中和@RequestMapping方法对应的【HandlerMethod】处理器类
 * 
 * @author qinlei
 * @date 2021/6/9 下午2:49
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;
	/**
	 * key：请求URI value：处理器类
	 */
	private Map<String, HandlerMethod> urlHandlerMap = new HashMap<String, HandlerMethod>();

	@Override
	public Object getHandler(HttpServletRequest request) {
		return urlHandlerMap.get(request.getRequestURI());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	public void init() {
		List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
		for (BeanDefinition bd : beanDefinitions) {
			Class<?> clazz = ReflectUtil.resolveType(bd.getClazzName());
			if (isHandler(clazz)) {
				RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
				Method[] methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					// 判断方法上是否存在注解@RequestMapping
					if (m.isAnnotationPresent(RequestMapping.class)) {
						StringBuffer sb = new StringBuffer();
						sb.append(classMapping.value());
						RequestMapping methodMapping = m.getAnnotation(RequestMapping.class);
						sb.append(methodMapping.value());
						// 封装HandlerMethod对象（当前Controller的实例对象+method对象）
						HandlerMethod hm = new HandlerMethod(beanFactory.getBean(bd.getBeanName()), m);
						urlHandlerMap.put(sb.toString(), hm);
					}
				}
			}
		}
	}

	/**
	 * 判断处理器是否标注 @Controller 和 @RequestMapping
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isHandler(Class<?> clazz) {
		return clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RequestMapping.class);
	}

}
