package com.course.common.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.course.common.core.enums.RequestHeaderEnum;
import com.course.common.core.exception.CheckedException;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Web工具类
 * 
 * @author qinlei
 * @date 2021/7/29 下午5:19
 */
@Slf4j
public class WebUtil {

	private final static String BASIC = "Basic ";
	private static final String BEARER_TYPE = "Bearer";

	/**
	 * 获取Request的header - token
	 * 
	 * @param request
	 * @return
	 */
	public static String getHeader_token(HttpServletRequest request) {
		if (request != null) {
			String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (StrUtil.isNotBlank(authorization)) {
				return authorization.replace(BEARER_TYPE, StrUtil.EMPTY).trim();
			}
		}
		return null;
	}

	/**
	 * 获取Request的header -CONTENT_TYPE 判断是否是json方式传递数据
	 *
	 * @param request
	 * @return
	 */
	public static boolean isApplicationJson(HttpServletRequest request) {
		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
		if (StrUtil.isNotBlank(contentType) && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取Request的header -ACTION_STATUS
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkActionStatusInit(HttpServletRequest request) {
		String actionStatus = getHeader(request, RequestHeaderEnum.ACTION_STATUS);
		return ("init".equals(actionStatus));
	}

	/**
	 * 获取Request的header -key
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getHeader(HttpServletRequest request, RequestHeaderEnum key) {
		return (request == null) ? null : request.getHeader(key.getCode());
	}

	/**
	 * 从request 获取CLIENT_ID
	 * 
	 * @param request
	 * @return
	 */
	@SneakyThrows
	public static String[] getClientId(ServerHttpRequest request) {
		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		return getClientId(header);
	}

	/**
	 * 获取客户端
	 * 
	 * @param header
	 * @return
	 */
	@SneakyThrows
	public static String[] getClientId(String header) {
		if (header == null || !header.startsWith(WebUtil.BASIC)) {
			throw new CheckedException("请求头中client信息为空");
		}
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new CheckedException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new CheckedException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

	/**
	 * 返回json
	 *
	 * @param response
	 * @param result:返回对象
	 *
	 */
	public static void responseWriteJson(HttpServletResponse response, Object result) {
		responseWriteJson(response, result, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 返回json
	 *
	 * @param response
	 * @param result:返回对象
	 * @param contentType：类型
	 *
	 */
	public static void responseWriteJson(HttpServletResponse response, Object result, String contentType) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.append(JSONUtil.toJsonStr(result));
			writer.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * 获取 HttpServletResponse
	 *
	 * @return {HttpServletResponse}
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
}
