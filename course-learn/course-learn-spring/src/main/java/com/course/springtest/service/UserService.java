package com.course.springtest.service;

import com.course.springtest.pojo.User;

import java.util.List;
import java.util.Map;


public interface UserService {

	List<User> queryUsers(Map<String, Object> param);
}
