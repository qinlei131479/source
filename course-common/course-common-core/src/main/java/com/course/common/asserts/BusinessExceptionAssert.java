package com.course.common.asserts;

import com.course.common.enums.IResponseEnum;
import com.course.common.exception.BaseException;
import com.course.common.exception.BusinessException;

import java.text.MessageFormat;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:19
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return null;
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return null;
    }
}
