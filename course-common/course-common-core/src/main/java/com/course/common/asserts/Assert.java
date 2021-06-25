package com.course.common.asserts;

import com.course.common.exception.BaseRuntimeException;

/**
 * @author qinlei
 * @date 2021/6/15 下午4:14
 */
public interface Assert {

	/**
	 * 创建异常
	 * 
	 * @return
	 */
	BaseRuntimeException newException();

	/**
	 * 创建异常
	 * 
	 * @param t
	 * @param args
	 * @return
	 */
	BaseRuntimeException newException(Throwable t, Object... args);

	/**
	 * <p>
	 * 断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj
	 *            待判断对象
	 */
	default void assertNotNull(Object obj) {
		if (obj == null) {
			throw newException();
		}
	}

	/**
	 * <p>
	 * 断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 * <p>
	 * 异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
	 * 
	 * @param obj
	 * @param args
	 * @throws BaseRuntimeException
	 */

	default void assertNotNull(Object obj, Object... args) {
		if (obj == null) {
			throw newException();
		}
	}

}
