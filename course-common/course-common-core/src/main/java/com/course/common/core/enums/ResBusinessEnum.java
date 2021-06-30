package com.course.common.core.enums;

import com.course.common.core.asserts.BusinessExceptionAssert;

/**
 * 接口返回对象Res枚举类型：业务异常
 * 
 * @author qinlei
 * @date 2021/6/15 下午4:20
 */
public enum ResBusinessEnum implements BusinessExceptionAssert {
	/**
	 * Bad licence type
	 */
	BAD_LICENCE_TYPE(7001, "Bad licence type"),
	/**
	 * Licence not found
	 */
	LICENCE_NOT_FOUND(7002, "Licence not found");

	ResBusinessEnum(Integer code, String message) {
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
