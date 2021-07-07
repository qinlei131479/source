package com.course.mvc.handler;

import com.course.mvc.handler.face.SimpleControllerHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qinlei
 * @date 2021/6/8 下午10:09
 */
public class AddUserHandler implements SimpleControllerHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write("AddUserHandler");
	}
}
