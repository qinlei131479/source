package com.course.common.exception;

import com.course.common.enums.CommonResponseEnum;
import com.course.common.enums.IResponseEnum;

import com.course.common.enums.ResEnum;
import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:16
 */
@Getter
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private ResEnum responseEnum;
	private String message;
	private Object[] args;
	private Throwable caus;

	public BaseException(ResEnum responseEnum) {
		this(responseEnum, null, responseEnum.getMsg(), null);
	}

	public BaseException(ResEnum responseEnum, Object[] args, String message) {
		this(responseEnum, args, message, null);
	}

	public BaseException(ResEnum responseEnum, Object[] args, String message, Throwable cause) {
		this.responseEnum = responseEnum;
		this.args = args;
		this.message = message;
		this.caus = cause;
	}
}
