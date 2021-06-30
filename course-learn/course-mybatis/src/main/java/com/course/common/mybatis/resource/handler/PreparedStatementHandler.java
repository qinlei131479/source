package com.course.common.mybatis.resource.handler;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.course.common.mybatis.resource.config.Configuration;
import com.course.common.mybatis.resource.config.MappedStatement;
import com.course.common.mybatis.resource.sqlsession.BoundSql;
import com.course.common.mybatis.resource.sqlsession.ParameterMapping;
import com.course.common.mybatis.resource.utils.SimpleTypeRegistry;

/**
 * @author qinlei
 * @date 2021/6/1 上午11:29
 */
public class PreparedStatementHandler implements StatementHandler {

	private ParameterHandler parameterHandler;
	private ResultSetHandler resultSetHandler;

	public PreparedStatementHandler(Configuration configuration) {
		this.parameterHandler = configuration.newParameterHandler();
		this.resultSetHandler = configuration.newResultSetHandler();
	}

	@Override
	public Statement prepare(Connection connection, String sql) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			return prepareStatement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet execute(Statement statement, String sql) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void parameterize(Statement statement, BoundSql boundSql, Object paramObject) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) statement;

			Class<?> parameterTypeClass = paramObject.getClass();
			// 先判断入参类型(8种基本类型、String类型、各种集合类型、自定义的Java类型)
			if (SimpleTypeRegistry.isSimpleType(parameterTypeClass)) {
				preparedStatement.setObject(1, paramObject);
			} else {
				// 自定义的Java类型
				// 此处需要遍历SQL语句中的参数列表
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				for (int i = 0; i < parameterMappings.size(); i++) {
					// 列名
					ParameterMapping param = parameterMappings.get(i);

					// 根据列名获取入参对象的属性值，前提：列名和属性名称要一致
					Field field = parameterTypeClass.getDeclaredField(param.getName());
					field.setAccessible(true);
					// 获取属性值
					Object value = field.get(paramObject);
					preparedStatement.setObject(i + 1, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Object> handleResultSet(MappedStatement mappedStatement, ResultSet resultSet) {
		try {
			List<Object> results = new ArrayList<Object>();

			// 获取要映射的结果类型
			Class<?> resultTypeClass = mappedStatement.getResultTypeClass();

			Object result = null;
			while (resultSet.next()) {
				// 每一行对应一个对象
				result = resultTypeClass.newInstance();

				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					// 获取结果集中列的名称
					String columnName = metaData.getColumnName(i);

					Field field = resultTypeClass.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(result, resultSet.getObject(columnName));

				}

				results.add(result);
			}

			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
