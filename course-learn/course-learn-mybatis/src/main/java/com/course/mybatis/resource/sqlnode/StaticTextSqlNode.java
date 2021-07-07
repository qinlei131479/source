package com.course.mybatis.resource.sqlnode;

/**
 * 封装不带${}的SQL文本
 * 
 * @author qinlei
 * @date 2021/5/31 下午4:14
 */
public class StaticTextSqlNode implements SqlNode {

	private String sqlText;

	public StaticTextSqlNode(String sqlText) {
		this.sqlText = sqlText;
	}

	@Override
	public void apply(DynamicContext context) {
		context.appendSql(sqlText);
	}

}
