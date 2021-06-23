package com.course.springboot.service;

import com.course.mybatis.service.UpService;
import com.course.springboot.entity.User;

/**
 * service接口：用户信息表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
public interface UserService extends UpService<User> {
}
