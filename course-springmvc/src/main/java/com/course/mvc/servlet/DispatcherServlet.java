package com.course.mvc.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.mvc.adapter.face.HandlerAdapter;
import com.course.mvc.mapping.face.HandlerMapping;
import com.course.springtest.factory.support.DefaultListableBeanFactory;
import com.course.springtest.reader.XmlBeanDefinitionReader;
import com.course.springtest.resource.ClasspathResource;
import com.course.springtest.resource.Resource;

/**
 * 前端控制器
 * 
 * @author qinlei
 * @date 2021/6/8 下午9:51
 */
public class DispatcherServlet extends AbstractHttpServlet {

	/**
	 * 存储的是所有的处理器映射器
	 */
	private List<HandlerMapping> handlerMappings = new ArrayList<>();
	/**
	 * 存储的是所有的处理器适配器
	 */
	private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

	private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

	private DefaultListableBeanFactory beanFactory;

	/**
	 * Servlet自身生命周期方法，在Servlet被Tomact实例化后执行此方法
	 * 
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// 1、加载Spring容器
		initSpringContainer(config);
		// 2、加载所有的HandlerMapping实例
		initStrategy();
	}
	private void initSpringContainer(ServletConfig config){
		beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);

		// 从web.xml中获取init-param标签的value值
		String location = config.getInitParameter(CONTEXT_CONFIG_LOCATION);
		Resource resource = new ClasspathResource(location);
		definitionReader.loadBeanDefinitions(resource.getResource());
	}

	private void initStrategy() {
		handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
		handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
	}

	@Override
	public void doDispath(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1、查找处理器(交给处理器映射器去查找)
		Object handler = getHandler(request);
		// 2、执行处理器方法（先要找到处理器适配器，再委托给处理器适配器去执行处理器方法）
		HandlerAdapter adapter = getHandlerAdapter(handler);
		// 3、执行并响应结果
		adapter.handleRequest(handler, request, response);
	}

	private HandlerAdapter getHandlerAdapter(Object handler) {
		// 优化后的处理器适配器
		if (handlerAdapters != null) {
			for (HandlerAdapter ha : handlerAdapters) {
				if (ha.support(handler)) {
					return ha;
				}
			}
		}
		// TODO
		// if (handler instanceof HttpRequestHandler) {
		// return new HttpRequestHandlerAdapter();
		// } else if (handler instanceof SimpleControllerHandler) {
		// return new SimpleControllerHandlerAdapter();
		// }
		return null;
	}

	/**
	 * 查找处理器
	 * 
	 * @param request
	 * @return
	 */
	private Object getHandler(HttpServletRequest request) {
		//
		if (handlerMappings != null) {
			for (HandlerMapping hm : handlerMappings) {
				Object handler = hm.getHandler(request);
				if (handler != null) {
					return handler;
				}
			}
		}
		// HandlerMapping hm = new BeanNameUrlHandlerMapping();
		// Object handler = hm.getHandler(request);
		// if (handler != null) {
		// return handler;
		// }
		// hm = new SimpleUrlHandlerMapping();
		// handler = hm.getHandler(request);
		// if (handler != null) {
		// return handler;
		// }
		return null;
	}
}
