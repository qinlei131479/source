package com.course.common.enums;

import lombok.Getter;

/**
 * 枚举类型 - 查询方式
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Getter
public enum ListTypeEnum {
	/** 分页 */
	page("分页"),
	/** 数组 */
	array("数组"),
	/** 单条 */
	one("单条");

	ListTypeEnum(String name) {
		this.code = this.name();
		this.name = name;
	}

	private String code;
	private String name;

}
