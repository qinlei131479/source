package com.course.mybatis.plus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.mybatis.plus.entity.User;
import com.course.mybatis.plus.mapper.UserMapper;
import com.course.mybatis.plus.service.IUserService;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
