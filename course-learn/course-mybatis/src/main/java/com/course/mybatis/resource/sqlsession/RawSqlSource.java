package com.course.mybatis.resource.sqlsession;

import com.course.mybatis.resource.sqlnode.DynamicContext;
import com.course.mybatis.resource.sqlnode.SqlNode;

/**
 * 封装并处理#{}的SQL信息 封装的数据的处理时机：只需要被处理一次就可以了，那么在构造方法去处理#{}再合适不过了
 * SELECT * FROM user WHERE id = #{id}
 * SELECT * FROM user WHERE id = ?
 * 
 * @author qinlei
 * @date 2021/5/31 下午3:47
 */
public class RawSqlSource implements SqlSource {

	private SqlSource sqlSource;

	public RawSqlSource(SqlNode rootSqlNode) {
		DynamicContext context = new DynamicContext(null);
		// 先将所有SqlNode里面存储的sql文本合并成一条完整的SQL语句（此时合并的SQL语句，依然带有#{}）
		rootSqlNode.apply(context);

		SqlSourceParser sqlSourceParser = new SqlSourceParser();
		sqlSource = sqlSourceParser.parse(context.getSql());

	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		return sqlSource.getBoundSql(paramObject);
	}
}
