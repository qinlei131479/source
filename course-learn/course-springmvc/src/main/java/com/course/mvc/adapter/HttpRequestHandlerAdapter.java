package com.course.mvc.adapter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.mvc.adapter.face.HandlerAdapter;
import com.course.mvc.handler.face.HttpRequestHandler;

/**
 * HttpRequestHandler的适配器
 *
 * @author qinlei
 * @date 2021/6/8 下午10:13
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {

	@Override
	public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpRequestHandler requestHandler = (HttpRequestHandler) handler;
		requestHandler.handleRequest(request, response);
	}

	@Override
	public boolean support(Object handler) {
		return handler instanceof HttpRequestHandler;
	}
}
