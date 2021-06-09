package com.course.mvc.adapter.face;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qinlei
 * @date 2021/6/8 下午10:00
 */
public interface HandlerAdapter {

	boolean support(Object handler);

	void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, Exception;
}
