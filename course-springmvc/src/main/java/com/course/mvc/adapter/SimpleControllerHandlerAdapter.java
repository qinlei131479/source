package com.course.mvc.adapter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.mvc.adapter.face.HandlerAdapter;
import com.course.mvc.handler.face.SimpleControllerHandler;

/**
 * @author qinlei
 * @date 2021/6/8 下午10:14
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {

	@Override
	public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SimpleControllerHandler controller = (SimpleControllerHandler) handler;
		controller.handleRequest(request, response);
	}

	@Override
	public boolean support(Object handler) {
		return handler instanceof SimpleControllerHandler;
	}
}
