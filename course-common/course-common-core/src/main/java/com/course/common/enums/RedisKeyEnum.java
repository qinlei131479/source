package com.course.common.enums;

import lombok.Getter;

/**
 * redis相关缓存key枚举
 * 
 * @author qinlei
 * @date 2021/6/25 下午10:10
 */
@Getter
public enum RedisKeyEnum {
	/**
	 * 短信
	 */
	smscode_("短信"),
	/**
	 * 短信
	 */
	validcode_("图形验证码"),
	/**
	 * 主键
	 */
	key("主键"),
	/**
	 * 接口日志
	 */
	apilog_list("接口日志");

	RedisKeyEnum(String msg) {
		this.code = this.name();
		this.msg = msg;
	}

	/**
	 * 缓存key，项目前缀
	 */
	public static final String PROJECT_PREFIX = "course:";
	/**
	 * 缓存key，oauth前缀
	 */
	public static final String OAUTH_PREFIX = PROJECT_PREFIX + "oauth:";
	/**
	 * 缓存key，oauth客户端信息
	 */
	public static final String CLIENT_DETAILS_KEY = OAUTH_PREFIX + "client";
	/**
	 * 缓存key，用户-角色id列表
	 */
	public static final String ADMIN_KEY = PROJECT_PREFIX + "admin";
	/**
	 * 缓存key，角色-接口路径列表
	 */
	public static final String ROLE_APIPATHS_KEY = PROJECT_PREFIX + "role_apipaths";
	/**
	 * 缓存key，数据权限
	 */
	public static final String SCOPE_KEY = PROJECT_PREFIX + "scope";
	/**
	 * 缓存key，平台+路径对应的接口
	 */
	public static final String PLATFORM_API_KEY = PROJECT_PREFIX + "platform_api";
	/**
	 * 缓存key，系统配置
	 */
	public static final String CONFIG_KEY = PROJECT_PREFIX + "config";
	/**
	 * 缓存key，系统配置
	 */
	public static final String ZONE = PROJECT_PREFIX + "zone";
	/**
	 * 缓存key，企业微信token
	 */
	public static final String WORKWEIXIN_TOKEN_KEY = PROJECT_PREFIX + "workweixin_token";
	/**
	 * 缓存key，系统配置 - apiConfig
	 */
	public static final String CONFIG_APICONFIG_KEY = "apiConfig";

	public String getKey() {
		return PROJECT_PREFIX + this.code;
	}

	public String getKey(String end) {
		return PROJECT_PREFIX + this.code.replace("_", "") + ":" + end;
	}

	/**
	 * 获取es的indexName
	 */
	public static String getEsIndexName(Class<?> clazz) {
		return PROJECT_PREFIX.replaceAll(":", "_") + clazz.getSimpleName().toLowerCase();
	}

	private String code;
	private String msg;
}
