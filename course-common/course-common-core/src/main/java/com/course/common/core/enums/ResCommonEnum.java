package com.course.common.core.enums;

import com.course.common.core.asserts.BusinessExceptionAssert;

/**
 * 接口返回对象Res枚举类型：公共异常
 * 
 * @author qinlei
 * @date 2021/6/16 下午5:36
 */
public enum ResCommonEnum implements BusinessExceptionAssert {
	/** 服务器已成功处理了请求 */
	SUCC(200, "SUCCESS"),
	/** 对象为空 */
	OBJECT_NULL(300,"object is null"),
	/** 未登录，客户端没有提供认证信息或者认证信息不正确 */
	NO_LOGIN(401, "未登录"),
	/** 客户端的权限不足，前台通常无法处理，可以直接提示 */
	NO_POWER(403, "没有权限"),
	/** 服务端异常 */
	FAIL(500, "服务端异常"),
	/** 服务端错误，前台通常无法处理，可以直接提示 */
	API_NOT_DEFINE(900, "接口未定义"),
	/** 接口未上线错误，前台通常无法处理，可以直接提示 */
	API_NOT_ONLINE(903, "接口未上线"),
	/** 服务端错误 */
	SERVER_ERROR(9999, "server error");

	ResCommonEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;
	private String message;

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.message;
	}
}
