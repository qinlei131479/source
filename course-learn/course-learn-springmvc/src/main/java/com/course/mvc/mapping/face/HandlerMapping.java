package com.course.mvc.mapping.face;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器映射器
 * 
 * @author qinlei
 * @date 2021/6/8 下午10:04
 */
public interface HandlerMapping {

	Object getHandler(HttpServletRequest request);
}
