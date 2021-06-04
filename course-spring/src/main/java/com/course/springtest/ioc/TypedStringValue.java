package com.course.springtest.ioc;

import lombok.Data;

/**
 * @author qinlei
 * @date 2021/6/4 下午5:02
 */
@Data
public class TypedStringValue {

	/**
	 * value属性值
	 */
	private String value;

	/**
	 * value属性值对应的真正类型（Bean中属性的类型）
	 */
	private Class<?> targetType;

	public TypedStringValue(String value) {
		this.value = value;
	}

}
