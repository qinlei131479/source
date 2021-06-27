package com.course.common.enums;

import lombok.Getter;

/**
 * 接口返回对象Res枚举类型
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Getter
public enum ActionStatusEnum {
	/**
	 * 初始化
	 */
	init("初始化"),
	/**
	 * 校验
	 */
	check("校验");

	ActionStatusEnum(String name) {
		this.code = this.name();
		this.name = name;
	}

	private String code;
	private String name;
}
