package com.course.sharding.jdbc.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
public interface UserService extends IService<User> {

	List<Department> getDeptByUserId(String userId);

}
