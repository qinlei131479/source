package com.course.common.exception;

import com.course.common.entity.Res;
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
}
