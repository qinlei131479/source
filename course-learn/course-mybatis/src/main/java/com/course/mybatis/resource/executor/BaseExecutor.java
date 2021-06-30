package com.course.mybatis.resource.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.course.mybatis.resource.config.Configuration;
import com.course.mybatis.resource.config.MappedStatement;
import com.course.mybatis.resource.sqlsession.BoundSql;
import com.course.mybatis.resource.sqlsession.SqlSource;

/**
 * @author qinlei
 * @date 2021/6/1 上午11:20
 */
public abstract class BaseExecutor implements Executor {

	private Map<String, List<Object>> oneLevelCache = new HashMap<>();

	@Override
	public <T> List<T> query(String statementId, Configuration configuration, Object param) {
		MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
		SqlSource sqlSource = mappedStatement.getSqlSource();
		BoundSql boundSql = sqlSource.getBoundSql(param);
		List<Object> list = oneLevelCache.get(boundSql.getSql());

		if (list != null && list.size() > 0) {
			return (List<T>) list;
		}
		list = queryFromDataBase(mappedStatement, configuration, boundSql, param);
		oneLevelCache.put(boundSql.getSql(), list);
		return (List<T>) list;
	}

	/**
	 * 具体执行方法抽象化
	 * 
	 * @param mappedStatement
	 * @param configuration
	 * @param boundSql
	 * @param param
	 * @return
	 */
	public abstract List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration,
			BoundSql boundSql, Object param);
}
