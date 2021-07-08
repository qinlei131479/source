package com.course.sharding.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.sharding.jdbc.entity.Department;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Mapper
public interface DepartmentMapper extends UpMapper<Department> {
	/**
	 * 模糊查询
	 * 
	 * @param name
	 * @return
	 */
	List<Department> listByParam(@Param("name") String name);
}
