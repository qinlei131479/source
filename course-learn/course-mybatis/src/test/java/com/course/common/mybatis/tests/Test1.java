package com.course.common.mybatis.tests;

import java.io.InputStream;

import com.course.common.mybatis.phase01.dao.UserDao;
import com.course.common.mybatis.phase01.dao.UserDaoImpl;
import com.course.common.mybatis.phase01.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试入门案例
 * 
 * @author think
 *
 */
public class Test1 {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws Exception {
		// 加载全局配置文件（同时把映射文件也加载了）
		String resource = "phase01/SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// sqlsessionFactory需要通过sqlsessionFactoryBuilder读取全局配置文件信息之后
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindUserById() {
		UserDao dao = new UserDaoImpl(sqlSessionFactory);
		User user = dao.findUserById(1);
		System.out.println(user);
	}

}
