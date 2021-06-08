package com.course.mvc.adapter;

import com.course.mvc.handler.SimpleControllerHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qinlei
 * @date 2021/6/8 下午10:14
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter{
    @Override
    public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleControllerHandler controller = (SimpleControllerHandler) handler;
        controller.handleRequest(request, response);
    }
}
