package com.course.mvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.mvc.adapter.HandlerAdapter;
import com.course.mvc.adapter.HttpRequestHandlerAdapter;
import com.course.mvc.adapter.SimpleControllerHandlerAdapter;
import com.course.mvc.handler.HttpRequestHandler;
import com.course.mvc.handler.SimpleControllerHandler;
import com.course.mvc.mapping.BeanNameUrlHandlerMapping;
import com.course.mvc.mapping.HandlerMapping;
import com.course.mvc.mapping.SimpleUrlHandlerMapping;

import java.io.IOException;

/**
 * 前端控制器
 * 
 * @author qinlei
 * @date 2021/6/8 下午9:51
 */
public class DispatcherServlet extends AbstractHttpServlet {

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
		// TODO
		if (handler instanceof HttpRequestHandler) {
			return new HttpRequestHandlerAdapter();
		} else if (handler instanceof SimpleControllerHandler) {
			return new SimpleControllerHandlerAdapter();
		}
		return null;
	}

	private Object getHandler(HttpServletRequest request) {
		HandlerMapping hm = new BeanNameUrlHandlerMapping();
		Object handler = hm.getHandler(request);
		if (handler != null) {
			return handler;
		}
		hm = new SimpleUrlHandlerMapping();
		handler = hm.getHandler(request);
		if (handler != null) {
			return handler;
		}
		// ....
		return null;
	}
}
