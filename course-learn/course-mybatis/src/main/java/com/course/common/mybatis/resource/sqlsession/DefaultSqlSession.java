package com.course.common.mybatis.resource.sqlsession;

import java.util.List;

import com.course.common.mybatis.resource.config.Configuration;
import com.course.common.mybatis.resource.executor.Executor;

/**
 * 默认的sql session 实现
 * 
 * @author qinlei
 * @date 2021/6/1 上午11:07
 */
public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <T> T selectOne(String statementId, Object param) {
		List<Object> list = this.selectList(statementId, param);
		if (list != null && list.size() == 1) {
			return (T) list.get(0);
		}
		return null;
	}

	@Override
	public <T> List<T> selectList(String statementId, Object param) {
		Executor executor = configuration.newExecutor();

		// 是否使用二级缓存 CachingExecutor
		// 是否使用一级缓存 BaseExecutor
		// 判断是批处理的执行方式、还是简单的执行方式(SimpleExecutor)、可重用的执行方式
		return executor.query(statementId, configuration, param);
	}
}
