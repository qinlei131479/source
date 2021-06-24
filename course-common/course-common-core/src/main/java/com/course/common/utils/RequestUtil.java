package com.course.common.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;

/**
 * 工具类：request
 *
 * @author channing
 * @date 2020/3/26 下午2:57
 */
public class RequestUtil {
	private static final Pattern PATTERN_SESSIONID = Pattern.compile(";jsessionid=[0-9a-zA-Z]*",
			Pattern.CASE_INSENSITIVE);
	private static final String BEARER_TYPE = "Bearer";

	/**
	 * 获取request
	 *
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			return request;
		}
		return null;
	}

	/**
	 * 计算接口url
	 *
	 * @param request
	 * @return [path, action]
	 */
	public static String[] calApiUrl(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		// 计算requestUri
		requestUri = PATTERN_SESSIONID.matcher(requestUri).replaceAll("");
		requestUri = requestUri.replaceFirst(request.getContextPath(), "");
		if (!requestUri.equals("") && !requestUri.equals("/")) {
			requestUri = requestUri.replaceAll("/+$", "");
		}
		// 这种接口url，只有前面部分为接口地址：/apifront/weixincallback/msg/{appid}
		String[] sp = requestUri.split("\\/");
		String path = sp[0] + "/" + sp[1] + "/" + sp[2];
		String action = sp[2];
		return new String[] { path, action };
	}

	/**
	 * 获取Request的header - token
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

}
