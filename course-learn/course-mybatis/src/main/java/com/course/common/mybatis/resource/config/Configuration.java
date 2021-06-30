package com.course.common.mybatis.resource.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;


import com.course.common.mybatis.resource.executor.CachingExecutor;
import com.course.common.mybatis.resource.executor.Executor;
import com.course.common.mybatis.resource.executor.SimpleExecutor;
import com.course.common.mybatis.resource.handler.ParameterHandler;
import com.course.common.mybatis.resource.handler.PreparedStatementHandler;
import com.course.common.mybatis.resource.handler.ResultSetHandler;
import com.course.common.mybatis.resource.handler.StatementHandler;

/**
 * @author qinlei
 * @date 2021/5/30 下午6:16
 */
public class Configuration {

	/**
	 * 是否使用缓存
	 */
	private boolean useCache = true;
	protected Properties variables = new Properties();
	private DataSource dataSource;
	private Map<String, MappedStatement> mappedStatements = new HashMap<String, MappedStatement>();

	public Properties getVariables() {
		return variables;
	}

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
		this.mappedStatements.put(statementId, mappedStatement);
	}

	/**
	 * 创建执行器
	 * 
	 * @return
	 */
	public Executor newExecutor() {
		// 根据类型判断是批处理的执行方式、还是简单的执行方式(SimpleExecutor)、可重用的执行方式
		Executor executor = null;
		// 如果Executor的类型是simple，那么创建SimpleExecutor
		executor = new SimpleExecutor();
		// 如果Executor的类型是batch，那么创建BatchExecutor
		if (useCache) {
			executor = new CachingExecutor(executor);
		}
		return executor;
	}

	public MappedStatement getMappedStatementById(String statementId) {
		return mappedStatements.get(statementId);
	}

	public StatementHandler newStatementHandler(String statementType) {
		if ("prepared".equals(statementType)) {
			return new PreparedStatementHandler(this);
		}
		return null;
	}

	public ParameterHandler newParameterHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSetHandler newResultSetHandler() {
		// TODO Auto-generated method stub
		return null;
	}
}
