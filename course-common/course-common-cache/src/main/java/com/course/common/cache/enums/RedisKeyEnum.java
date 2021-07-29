package com.course.common.cache.enums;

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
	validCode("图形验证码"),
	/**
	 * 主键
	 */
	key("主键"),
	/**
	 * 接口日志
	 */
	apiLogList("接口日志");

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
	 * 缓存key，系统配置
	 */
	public static final String CONFIG_KEY = PROJECT_PREFIX + "config";

	public String getKey() {
		return PROJECT_PREFIX + this.code;
	}

	public String getKey(String end) {
		return PROJECT_PREFIX + this.code + ":" + end;
	}

	private String code;
	private String msg;
}
