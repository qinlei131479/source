package com.course.common.enums;

import com.course.common.asserts.BusinessExceptionAssert;

import lombok.AllArgsConstructor;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:20
 */
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {
	/**
	 * Bad licence type
	 */
	BAD_LICENCE_TYPE(7001, "Bad licence type"),
	/**
	 * Licence not found
	 */
	LICENCE_NOT_FOUND(7002, "Licence not found");

	ResponseEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;
	private String message;

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
