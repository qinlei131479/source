package com.course.mybatis.phase01.dao;


import com.course.mybatis.phase01.pojo.User;

public interface UserDao {

	User findUserById(int id);
}
