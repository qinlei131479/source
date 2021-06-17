package com.course.mybatis.resource.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.course.mybatis.resource.config.MappedStatement;
import com.course.mybatis.resource.sqlsession.BoundSql;

/**
 *
 * @author qinlei
 * @date 2021/6/1 上午11:28
 */
public interface StatementHandler {

	Statement prepare(Connection connection, String sql);

	ResultSet execute(Statement statement, String sql);

	void parameterize(Statement statement, BoundSql boundSql, Object param);

	List<Object> handleResultSet(MappedStatement mappedStatement, ResultSet resultSet);
}
