package com.course.springtest.service;

import java.util.List;
import java.util.Map;

import com.course.springtest.dao.UserDao;
import com.course.springtest.pojo.User;

public class UserServiceImpl implements UserService {

	/**
	 * 依赖注入UserDao
	 */
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> queryUsers(Map<String, Object> param) {
		return userDao.queryUserList(param);
	}

}
