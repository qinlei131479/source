package com.course.biz.sys.enums;

import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/28 下午3:46
 */
@Getter
public enum ApiRunModeEnum {
	//
	develop("开发环境"),
	//
	test("测试环境"),
	//
	product("生产环境");

	ApiRunModeEnum(String name) {
		this.code = this.name();
		this.name = name;
	}

	private String code;
	private String name;
}
