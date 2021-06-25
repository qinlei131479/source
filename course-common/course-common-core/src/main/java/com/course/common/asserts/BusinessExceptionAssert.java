package com.course.common.asserts;

import java.text.MessageFormat;

import com.course.common.enums.IResponseEnum;
import com.course.common.exception.BaseRuntimeException;
import com.course.common.exception.BusinessException;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:19
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

	@Override
	default BaseRuntimeException newException() {
		return new BusinessException(this, this.getMsg());
	}

	@Override
	default BaseRuntimeException newException(Throwable t, Object... args) {
		String msg = MessageFormat.format(this.getMsg(), args);
		return new BusinessException(this, msg, t);
	}
}
