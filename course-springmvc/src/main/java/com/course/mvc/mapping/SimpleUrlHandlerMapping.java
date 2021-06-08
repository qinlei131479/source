package com.course.mvc.mapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过bean标签的；props属性去查找处理器映射器
 * 
 * @author qinlei
 * @date 2021/6/8 下午10:06
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {
	@Override
	public Object getHandler(HttpServletRequest request) {
		return null;
	}
}
