package com.course.sharding.jdbc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.User;
import com.course.sharding.jdbc.mapper.UserMapper;
import com.course.sharding.jdbc.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	private final UserMapper userMapper;

	@Override
	public List<Department> getDeptByUserId(String userId) {
		return userMapper.getDeptByUserId(userId);
	}
}
