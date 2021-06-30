package com.course.common.mybatis.resource.sqlsession;

import com.course.common.mybatis.resource.sqlnode.DynamicContext;
import com.course.common.mybatis.resource.sqlnode.SqlNode;

/**
 * 封装并处理${}和动态标签 封装的数据的处理时机：每次getBoundSql都会处理一次MixedSqlNode SELECT * FROM user
 * WHERE id = ${id} SELECT * FROM user WHERE id = + id的值（入参）
 *
 * @author qinlei
 * @date 2021/5/31 下午3:47
 */
public class DynamicSqlSource implements SqlSource {

	/**
	 * 节点信息，需要在处理SqlSource的时候，再去处理SqlNode，所以在此保存
	 */
	private SqlNode rootSqlNode;

	public DynamicSqlSource(SqlNode rootSqlNode) {
		this.rootSqlNode = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		DynamicContext context = new DynamicContext(paramObject);
		// 先将所有SqlNode里面存储的sql文本合并成一条完整的SQL语句（此时合并的SQL语句，依然带有#{}）
		rootSqlNode.apply(context);

		System.out.println(context.getSql());
		SqlSourceParser sqlSourceParser = new SqlSourceParser();
		SqlSource sqlSource = sqlSourceParser.parse(context.getSql());

		return sqlSource.getBoundSql(paramObject);
	}
}
