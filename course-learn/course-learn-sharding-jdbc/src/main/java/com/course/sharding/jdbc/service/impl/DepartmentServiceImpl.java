package com.course.sharding.jdbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.mapper.DepartmentMapper;
import com.course.sharding.jdbc.service.DepartmentService;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> listByParam(String name) {
		return departmentMapper.listByParam(name);
	}
}
