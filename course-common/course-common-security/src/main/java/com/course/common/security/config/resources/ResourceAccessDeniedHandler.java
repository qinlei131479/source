package com.course.common.security.config.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 授权拒绝处理器
 * 
 * @author qinlei
 * @date 2021/8/7 下午6:44
 */
@Slf4j
@Component
public class ResourceAccessDeniedHandler extends OAuth2AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException)
			throws IOException, ServletException {
		log.info("授权失败，禁止访问 {}", request.getRequestURI());
		response.setStatus(HttpStatus.HTTP_FORBIDDEN);
		WebUtil.responseWriteJson(response, Res.fail("授权失败，禁止访问"));

	}
}
