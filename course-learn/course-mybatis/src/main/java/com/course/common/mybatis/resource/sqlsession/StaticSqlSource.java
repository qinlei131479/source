package com.course.common.mybatis.resource.sqlsession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:56
 */
public class StaticSqlSource implements SqlSource {

	/**
	 * 已经解析完成的SQL语句
	 */
	private String sql;

	/**
	 * 解析#{}时，产生的参数信息集合
	 */
	private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

	public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
		super();
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		return new BoundSql(sql, parameterMappings);
	}
}
