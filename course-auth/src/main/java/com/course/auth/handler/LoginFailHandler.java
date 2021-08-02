package com.course.auth.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.course.common.core.constant.CommonConstants;
import com.course.common.core.entity.Res;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

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
		response.setContentType(CommonConstants.CONTENT_TYPE);
		PrintWriter writer = response.getWriter();
		writer.write(JSONUtil.toJsonStr(Res.fail(e.getMessage())));
		writer.flush();
		writer.close();
	}
}
