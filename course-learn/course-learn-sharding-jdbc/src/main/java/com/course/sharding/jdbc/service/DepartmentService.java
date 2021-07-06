package com.course.sharding.jdbc.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.sharding.jdbc.entity.Department;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
public interface DepartmentService extends IService<Department> {
	List<Department> listByParam(String name);
}
