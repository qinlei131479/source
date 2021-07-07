package com.course.sharding.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 按userId查询
	 * 
	 * @param userId
	 * @return
	 */
	List<Department> getDeptByUserId(@Param("userId") String userId);
}