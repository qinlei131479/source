package com.course.common.core.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.course.common.core.enums.RequestAttrEnum;
import com.course.common.core.enums.RequestHeaderEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;

/**
 * 工具类：request
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
public class RequestUtil {

	private static final String UNKNOWN = "unknown";
	private static final String BEARER_TYPE = "Bearer";
	private static final Pattern PATTERN_SESSIONID = Pattern.compile(";jsessionid=[0-9a-zA-Z]*",
			Pattern.CASE_INSENSITIVE);

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
	 * 获取Request的header
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getHeader(HttpServletRequest request, RequestHeaderEnum key) {
		return (request == null) ? null : request.getHeader(key.getCode());
	}

	/**
	 * 设置Request的attribute
	 * 
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void setAttr(HttpServletRequest request, RequestAttrEnum key, Object obj) {
		if (request != null && obj != null) {
			request.setAttribute(key.getCode(), obj);
		}
	}

	/**
	 * 设置Request的attribute
	 * 
	 * @param key
	 * @param obj
	 */
	public static void setAttr(RequestAttrEnum key, Object obj) {
		setAttr(getRequest(), key, obj);
	}

	/**
	 * 获取Request的attribute
	 * 
	 * @param request
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> T getAttr(HttpServletRequest request, RequestAttrEnum key) {
		return request == null ? null : (T) request.getAttribute(key.getCode());
	}

	/**
	 * 获取Request的attribute
	 * 
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> T getAttr(RequestAttrEnum key) {
		return getAttr(getRequest(), key);
	}

	/**
	 * IP解析
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return UNKNOWN;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}
}
