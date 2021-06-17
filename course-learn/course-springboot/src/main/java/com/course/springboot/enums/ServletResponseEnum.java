package com.course.springboot.enums;

/**
 * @author qinlei
 * @date 2021/6/16 下午5:47
 */
public enum ServletResponseEnum implements IResponseEnum {

	SERVER_ERROR(9999, "server error."),

	VALID_ERROR(9000, "valid error"),

	/**
	 * Bad licence type
	 */
	BAD_LICENCE_TYPE(7001, "Bad licence type."),
	/**
	 * Licence not found
	 */
	LICENCE_NOT_FOUND(7002, "Licence not found.");

	ServletResponseEnum(int code, String message) {
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
