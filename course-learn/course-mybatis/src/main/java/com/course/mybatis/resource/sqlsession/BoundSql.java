package com.course.mybatis.resource.sqlsession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:44
 */
public class BoundSql {

	/**
	 * 已经解析完成的SQL语句
	 */
	private String sql;

	/**
	 * 解析#{}时，产生的参数信息集合
	 */
	private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

	public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void addParameterMapping(ParameterMapping parameterMapping) {
		this.parameterMappings.add(parameterMapping);
	}

}
