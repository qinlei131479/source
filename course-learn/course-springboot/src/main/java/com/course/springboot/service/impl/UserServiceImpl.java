package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.User;
import com.course.springboot.mapper.UserMapper;
import com.course.springboot.service.UserService;

/**
 * service实现类：用户信息表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Service
public class UserServiceImpl extends UpServiceImpl<UserMapper, User> implements UserService {
}
