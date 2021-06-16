package com.course.springboot.exception;

import com.course.springboot.enums.IResponseEnum;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:18
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
		super(responseEnum, args, message);
	}

	public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
		super(responseEnum, args, message, cause);
	}
}
