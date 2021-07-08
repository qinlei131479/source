package com.course.sharding.jdbc.service;

import java.util.List;

import com.course.common.mybatis.service.UpService;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
public interface UserService extends UpService<User> {
	/**
	 * 根据userId查询
	 * 
	 * @param userId
	 * @return
	 */
	List<Department> getDeptByUserId(Long userId);

}
