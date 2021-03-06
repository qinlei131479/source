package com.course.mybatis.phase01.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.course.mybatis.phase01.pojo.User;

public class UserDaoImpl implements UserDao {

	private SqlSessionFactory sqlSessionFactory;

	// 注入sqlSessionFactory
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(int id) {
		// sqlsessionFactory工厂类去创建sqlsession会话
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// sqlsession接口，开发人员使用它对数据库进行增删改查操作
		User user = sqlSession.selectOne("test.findUserById", id);
		return user;
	}

}
