package com.course.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * code name对象
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeName {
	private Object code;
	private Object name;
	private Object groupIndex;

	public CodeName(Object code, Object name) {
		super();
		this.code = code;
		this.name = name;
	}
}
