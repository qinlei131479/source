package com.course.common.mybatis.resource.factory;

import com.course.common.mybatis.resource.config.Configuration;
import com.course.common.mybatis.resource.sqlsession.DefaultSqlSession;
import com.course.common.mybatis.resource.sqlsession.SqlSession;

/**
 * @author qinlei
 * @date 2021/5/31 上午11:00
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private final Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlSession openSession() {
		return new DefaultSqlSession(configuration);
	}
}
