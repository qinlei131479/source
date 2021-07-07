package com.course.sharding.jdbc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.mapper.DepartmentMapper;
import com.course.sharding.jdbc.service.DepartmentService;

import lombok.RequiredArgsConstructor;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

	private final DepartmentMapper departmentMapper;

	@Override
	public List<Department> listByParam(String name) {
		return departmentMapper.listByParam(name);
	}
}
