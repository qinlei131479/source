package com.course.common.enums;

/**
 * @author qinlei
 * @date 2021/6/16 下午5:36
 */
public enum CommonResponseEnum implements IResponseEnum {

	SERVER_ERROR(9999, "server error"),

	VALID_ERROR(9000, "valid error");

	CommonResponseEnum(int code, String message) {
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
