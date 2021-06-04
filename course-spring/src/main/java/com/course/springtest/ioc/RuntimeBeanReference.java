package com.course.springtest.ioc;

import lombok.Data;

/**
 * @author qinlei
 * @date 2021/6/4 下午5:01
 */
@Data
public class RuntimeBeanReference {

	/**
	 * ref的属性值
	 */
	private String ref;

	public RuntimeBeanReference(String ref) {
		this.ref = ref;
	}
}
