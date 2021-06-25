package com.course.common.exception;

import com.course.common.entity.Res;
import com.course.common.enums.IResponseEnum;

import lombok.Data;

/**
 * 自定义运行时异常
 * 
 * @author qinlei
 * @date 2021/6/23 下午12:36
 */
@Data
public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Res<?> res;

	public BaseRuntimeException(Res<?> res) {
		super(res.getMsg());
		this.res = res;
	}

	public BaseRuntimeException(IResponseEnum responseEnum) {
		this(responseEnum, null);
	}

	public BaseRuntimeException(IResponseEnum responseEnum, String message) {
		this(responseEnum, message, null);
	}

	public BaseRuntimeException(IResponseEnum responseEnum, String message, Throwable cause) {
		super(responseEnum.getMessage());
		res = Res.exception(responseEnum, message, cause);
	}
}
