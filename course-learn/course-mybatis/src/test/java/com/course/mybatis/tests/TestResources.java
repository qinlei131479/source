package com.course.mybatis.tests;

import java.io.InputStream;

import com.course.mybatis.phase02.pojo.User;
import com.course.mybatis.resource.io.Resources;
import org.junit.Before;
import org.junit.Test;

import com.course.mybatis.resource.factory.SqlSessionFactory;
import com.course.mybatis.resource.factory.SqlSessionFactoryBuilder;
import com.course.mybatis.resource.sqlsession.SqlSession;

/**
 * 测试入门案例
 * 
 * @author think
 *
 */
public class TestResources {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws Exception {
		// 加载全局配置文件（同时把映射文件也加载了）
		String resource = "phase02/SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// sqlsessionFactory需要通过sqlsessionFactoryBuilder读取全局配置文件信息之后
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindUserById() {
		// 创建UserMapper对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne("com.course.mybatis.phase02.mapper.UserMapper.findUserById", 1L);
		// 调用UserMapper对象的API
		System.out.println(user);
	}

}
