package com.course.springtest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.course.springtest.factory.support.DefaultListableBeanFactory;
import com.course.springtest.pojo.User;
import com.course.springtest.reader.XmlBeanDefinitionReader;
import com.course.springtest.resource.ClasspathResource;
import com.course.springtest.resource.Resource;
import com.course.springtest.service.UserService;

public class SpringTestV3 {

	@Test
	public void test() throws Exception {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 读取XML的BeanDefinition阅读器
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

		Resource resource = new ClasspathResource("beans.xml");
		InputStream inputStream = resource.getResource();

		beanDefinitionReader.loadBeanDefinitions(inputStream);

		// 根据用户名称查询用户信息
		UserService userService = (UserService) beanFactory.getBean("userService");

		// 入参对象
		Map<String, Object> param = new HashMap<>();
		param.put("name", "admin");
		// 根据用户名称查询用户信息
		List<User> users = userService.queryUsers(param);
		System.out.println(users);
	}
}
