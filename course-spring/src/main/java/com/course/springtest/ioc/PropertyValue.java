package com.course.springtest.ioc;

import lombok.Data;

/**
 * @author qinlei
 * @date 2021/6/4 下午4:58
 */
@Data
public class PropertyValue {

	private String name;

	private Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}
}
