package com.course.sharding.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.User;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Mapper
public interface UserMapper extends UpMapper<User> {

	/**
	 * 按userId查询
	 * 
	 * @param userId
	 * @return
	 */
	List<Department> getDeptByUserId(Long userId);
}
