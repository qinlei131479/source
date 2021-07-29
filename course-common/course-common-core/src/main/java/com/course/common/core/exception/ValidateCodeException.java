package com.course.common.core.exception;

/**
 * 验证码异常
 * 
 * @author qinlei
 * @date 2021/7/29 下午5:27
 */
public class ValidateCodeException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException() {
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
