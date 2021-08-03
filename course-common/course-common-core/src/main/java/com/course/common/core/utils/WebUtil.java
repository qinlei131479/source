package com.course.common.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.course.common.core.exception.CheckedException;

import cn.hutool.core.codec.Base64;
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

	/**
	 * 从request 获取CLIENT_ID
	 *
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
}
