package com.course.mvc.handler.face;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qinlei
 * @date 2021/6/8 下午10:16
 */
public interface SimpleControllerHandler {

    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
