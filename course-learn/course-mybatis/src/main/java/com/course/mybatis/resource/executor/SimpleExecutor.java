package com.course.mybatis.resource.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.course.mybatis.resource.config.Configuration;
import com.course.mybatis.resource.config.MappedStatement;
import com.course.mybatis.resource.handler.StatementHandler;
import com.course.mybatis.resource.sqlsession.BoundSql;

/**
 * 简单执行器
 * 
 * @author qinlei
 * @date 2021/6/1 上午11:20
 */
public class SimpleExecutor extends BaseExecutor {

	@Override
	public List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration,
                                          BoundSql boundSql, Object param) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		// 要返回的结果集合
		List<Object> results = new ArrayList<Object>();
		try {
			// 获取数据源对象，通过数据源获取连接
			connection = getConnection(configuration.getDataSource());
			String sql = boundSql.getSql();
			StatementHandler statementHandler = configuration.newStatementHandler(mappedStatement.getStatementType());
			statement = statementHandler.prepare(connection, sql);

			statementHandler.parameterize(statement, boundSql, param);

			resultSet = statementHandler.execute(statement, sql);

			results = statementHandler.handleResultSet(mappedStatement, resultSet);

			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param dataSource
	 * @return
	 */
	private Connection getConnection(DataSource dataSource) {
		try {
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
