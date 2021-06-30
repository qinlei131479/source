package com.course.common.mybatis.resource.sqlsession;

import java.util.List;

/**
 * @author qinlei
 * @date 2021/5/30 下午6:03
 */
public interface SqlSession {

	/**
	 * 查询单个结果
	 *
	 * @param statementId
	 * @param param
	 * @return
	 */
	<T> T selectOne(String statementId, Object param);

	/**
	 * 查询集合数据
	 *
	 * @param statementId
	 * @param param
	 * @return
	 */
	<T> List<T> selectList(String statementId, Object param);
}
