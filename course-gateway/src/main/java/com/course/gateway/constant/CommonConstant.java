package com.course.gateway.constant;

/**
 * @author qinlei
 * @date 2021/7/29 下午4:58
 */
public interface CommonConstant {

	/**
	 * 拦截form特殊参数
	 */
	String FORM = "from";
	/**
	 * password：登录密码解决字段
	 */
	String PASSWORD = "password";
	/**
	 * 密码加解密算法
	 */
	String KEY_ALGORITHM = "AES";

	/**
	 * 拦截登录接口
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";
	/**
	 * grant_type
	 */
	String GRANT_TYPE = "grant_type";
	/**
	 * refresh_token
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * BASIC
	 */
	String BASIC = "Basic ";
}
