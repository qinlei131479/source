package com.course.common.redission.exception;

/**
 * @author qinlei
 * @date 2021/7/21 下午10:58
 */
public class LockException extends RuntimeException {

	public LockException() {
	}

	public LockException(String message) {
		super(message);
	}

	public LockException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockException(Throwable cause) {
		super(cause);
	}

	public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
