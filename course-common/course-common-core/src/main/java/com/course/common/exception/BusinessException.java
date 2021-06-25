package com.course.common.exception;

import com.course.common.enums.IResponseEnum;
import com.course.common.enums.ResponseEnum;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:18
 */
public class BusinessException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(IResponseEnum resEnum, String message) {
		super(resEnum, message, null);
	}

	public BusinessException(IResponseEnum resEnum, String message, Throwable cause) {
		super(resEnum, message, cause);
	}
}
