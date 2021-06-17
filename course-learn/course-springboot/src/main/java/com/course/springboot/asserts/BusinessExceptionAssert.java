package com.course.springboot.asserts;

import com.course.springboot.enums.IResponseEnum;
import com.course.springboot.exception.BaseException;
import com.course.springboot.exception.BusinessException;

import java.text.MessageFormat;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:19
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg, t);
    }
}
