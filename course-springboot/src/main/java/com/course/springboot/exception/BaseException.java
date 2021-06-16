package com.course.springboot.exception;

import com.course.springboot.enums.CommonResponseEnum;
import com.course.springboot.enums.IResponseEnum;

import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:16
 */
@Getter
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private IResponseEnum responseEnum;
	private String message;
	private Object[] args;
	private Throwable caus;

	public BaseException(CommonResponseEnum responseEnum) {
		this(responseEnum, null, responseEnum.getMessage(), null);
	}

	public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
		this(responseEnum, args, message, null);
	}

	public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
		this.responseEnum = responseEnum;
		this.args = args;
		this.message = message;
		this.caus = cause;
	}
}
