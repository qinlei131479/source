package com.course.mybatis.phase02.mapper;


import com.course.mybatis.phase02.pojo.User;

public interface UserMapper {

	User findUserById(int id);
}
