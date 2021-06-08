package com.course.mvc.mapping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.course.mvc.handler.AddUserHandler;
import com.course.mvc.handler.QueryUserHandler;

/**
 * 通过bean标签的name属性去查找处理器映射器
 * 
 * @author qinlei
 * @date 2021/6/8 下午10:04
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping {

	/**
	 * key：请求URI value：处理器类
	 */
	private Map<String, Object> urlHandlerMap = new HashMap<String, Object>();

	public BeanNameUrlHandlerMapping() {
		init();
	}

	public void init() {
		// TODO
		urlHandlerMap.put("/queryUser", new QueryUserHandler());
		urlHandlerMap.put("/saveUser", new AddUserHandler());
	}

	@Override
	public Object getHandler(HttpServletRequest request) {
		return urlHandlerMap.get(request.getRequestURI());
	}
}
