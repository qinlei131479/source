package com.course.common.enums;

import lombok.Getter;

/**
 * 自定义Request header key
 * 
 * @author qinlei
 * @date 2021/6/28 下午4:16
 */
@Getter
public enum RequestAttrEnum {

	/** 令牌 */
	token("令牌"),
	/** post请求对象 */
	bodyObject("post请求对象"),
	/** post请求文本 */
	bodyString("post请求文本"),
	/** feign接口日志 */
	feignApiLog("feign接口日志");

	RequestAttrEnum(String name) {
		this.code = this.name();
		this.name = name;
	}

	private String code;
	private String name;

}
