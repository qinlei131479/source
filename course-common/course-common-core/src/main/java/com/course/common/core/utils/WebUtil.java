package com.course.common.core.utils;

import java.nio.charset.StandardCharsets;

import com.course.common.core.exception.CheckedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;


import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;

/**
 * Web工具类
 * 
 * @author qinlei
 * @date 2021/7/29 下午5:19
 */
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
}
