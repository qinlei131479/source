package com.course.mybatis.resource.factory;

import com.course.mybatis.resource.config.Configuration;
import com.course.mybatis.resource.sqlsession.DefaultSqlSession;
import com.course.mybatis.resource.sqlsession.SqlSession;

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
