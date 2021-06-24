package com.course.common.entity;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证元素
 * 
 * @author qinlei
 * @date 2021/6/24 下午5:26
 */
@Data
@AllArgsConstructor
public class ValidItem {

	/**
	 * 邮箱正则表达式
	 */
	public static final String REGEXP_EMAIL = "^$|.+@.+\\..+";
	/**
	 * 手机号正则表达式
	 */
	public static final String REGEXP_MOBILE = "^1[3456789]\\d{9}$";

	private String type;
	private Map<String, Object> rule;
}
