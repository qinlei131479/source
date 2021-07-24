package com.course.sharding.jdbc.service;

import java.util.List;

import com.course.common.mybatis.service.UpService;
import com.course.sharding.jdbc.entity.Department;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
public interface DepartmentService extends UpService<Department> {
	/**
	 * 按照参数查询
	 * 
	 * @param name
	 * @return
	 */
	List<Department> listByParam(String name);
}
