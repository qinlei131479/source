package com.course.common.enums;

import lombok.Getter;

/**
 * 枚举类型 - 0、1标志
 * 
 * @author qinlei
 * @date 2021/6/25 下午9:41
 */
@Getter
public enum FlagEnum {

	/** 是 */
	yes(1, "是"),
	/** 否 */
	no(0, "否");

	FlagEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;
	private String name;

	/**
	 * 判断flag是否等于yes
	 *
	 * @param flag
	 * @return
	 */
	public static boolean checkYes(Integer flag) {
		return FlagEnum.yes.getCode().equals(flag);
	}

	/**
	 * 判断flag是否等于no
	 *
	 * @param flag
	 * @return
	 */
	public static boolean checkNo(Integer flag) {
		return FlagEnum.no.getCode().equals(flag);
	}
}
