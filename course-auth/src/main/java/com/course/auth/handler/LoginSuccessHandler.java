package com.course.auth.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.course.common.core.constant.CommonConstants;
import com.course.common.core.entity.Res;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

/**
 * 自定义登录成功后返回json
 * 
 * @author qinlei
 * @date 2021/8/2 下午4:00
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType(CommonConstants.CONTENT_TYPE);
		PrintWriter writer = response.getWriter();
		writer.write(JSONUtil.toJsonStr(Res.succ(authentication)));
		writer.flush();
		writer.close();
	}
}
