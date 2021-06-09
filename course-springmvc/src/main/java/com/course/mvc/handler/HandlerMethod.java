package com.course.mvc.handler;

import java.lang.reflect.Method;

/**
 * 这个类的作用只是用来封装请求对应的Controller类的实例对象和方法对象
 * 
 * @author qinlei
 * @date 2021/6/9 下午2:52
 */
public class HandlerMethod {

	/**
	 * Controller实例对象
	 */
	private Object controller;

	/**
	 * RequestMapping注解的方法对象
	 */
	private Method method;

	public HandlerMethod(Object controller, Method method) {
		super();
		this.controller = controller;
		this.method = method;
	}

	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
