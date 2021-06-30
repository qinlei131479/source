package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.User;
import com.course.common.mybatis.plus.mapper.UserMapper;
import com.course.common.mybatis.plus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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