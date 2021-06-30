package com.course.common.mybatis.resource.sqlsession;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:45
 */
public class ParameterMapping {

	/**
	 * #{}中的名称
	 */
	private String name;

	private Class<?> type;

	public ParameterMapping(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
}
