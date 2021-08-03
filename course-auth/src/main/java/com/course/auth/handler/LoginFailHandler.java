package com.course.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;

/**
 *
 * 自定义登录失败返回json
 * 
 * @author qinlei
 * @date 2021/8/2 下午4:04
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		WebUtil.responseWriteJson(response, Res.fail(e.getMessage()));
	}
}
