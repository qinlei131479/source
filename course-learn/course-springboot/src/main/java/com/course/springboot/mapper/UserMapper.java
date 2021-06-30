package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.springboot.entity.User;

/**
 * Mapper接口：用户信息表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Mapper
public interface UserMapper extends UpMapper<User> {
}
