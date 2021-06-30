package com.course.common.mybatis.resource.factory;

import com.course.common.mybatis.resource.sqlsession.SqlSession;

/**
 *
 * @author qinlei
 * @date 2021/5/30 下午6:02
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
