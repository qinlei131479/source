package com.course.sharding.jdbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.User;
import com.course.sharding.jdbc.mapper.UserMapper;
import com.course.sharding.jdbc.service.UserService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Department> getDeptByUserId(String userId) {
		return userMapper.getDeptByUserId(userId);
	}
}
