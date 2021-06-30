package com.course.mybatis.resource.sqlsession;

public interface SqlSource {

	/**
	 * 返回BoundSql而不是sql语句，主要是针对#{}的解析过程中，需要存储解析过程产生的参数名称
	 * 
	 * "SELECT * FROM user WHERE id =" + id "SELECT * FROM user WHERE id =?"
	 * 
	 * @param paramObject
	 * @return
	 */
	BoundSql getBoundSql(Object paramObject);

}
