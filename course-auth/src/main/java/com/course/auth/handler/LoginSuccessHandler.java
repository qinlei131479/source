package com.course.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;
import com.course.common.security.entity.CourseUser;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义登录成功后返回json<br>
 * 1、在自定义loginPage和loginProcessingUrl模式下，
 * 自定义LoginSuccessHandler继承SavedRequestAwareAuthenticationSuccessHandler
 * 调用super.onAuthenticationSuccess(request, response, authentication)，
 * 避免出现requestCache在登录前和登录成功后不一致，导致无法跳转到redirect_uri=https://www.baidu.com地址
 * 
 * @author qinlei
 * @date 2021/8/2 下午4:00
 */
@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CourseUser user = (CourseUser) authentication.getPrincipal();
		if (WebUtil.isApplicationJson(request)) {
			WebUtil.responseWriteJson(response, Res.succ(user));
		} else {
			request.getSession().setAttribute("user", authentication);
			super.onAuthenticationSuccess(request, response, authentication);
			log.error("表单登录成功:{}", JSONUtil.toJsonStr(user));
			// 获取请求参数中是否包含 回调地址
			// String redirectUrl = request.getParameter(REDIRECT_URI);
			// redirectUrl = StrUtil.isNotBlank(redirectUrl) ?
			// redirectUrl :
			// "/user/index";
			// response.sendRedirect(redirectUrl);
		}
	}
}
