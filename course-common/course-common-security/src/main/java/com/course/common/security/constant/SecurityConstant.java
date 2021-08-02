package com.course.common.security.constant;

/**
 * 配置Security相关常用属性
 * 
 * @author qinlei
 * @date 2021/8/2 下午1:17
 */
public interface SecurityConstant {

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

    /***
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";
}
