package com.course.mybatis.resource.factory;

import com.course.mybatis.resource.sqlsession.SqlSession;

/**
 *
 * @author qinlei
 * @date 2021/5/30 下午6:02
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
