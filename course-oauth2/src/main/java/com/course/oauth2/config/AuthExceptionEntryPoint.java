package com.course.oauth2.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author qinlei
 * @date 2021/8/10 下午1:06
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Map map = new HashMap();
		map.put("code", "401");
		map.put("msg", authException.getMessage());
		map.put("path", request.getServletPath());
		map.put("timestamp", String.valueOf(System.currentTimeMillis()));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			throw new ServletException();
		}
	}
}
