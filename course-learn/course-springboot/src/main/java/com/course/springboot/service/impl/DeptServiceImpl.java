package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Dept;
import com.course.springboot.mapper.DeptMapper;
import com.course.springboot.service.DeptService;

/**
 * service实现类：部门表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Service
public class DeptServiceImpl extends UpServiceImpl<DeptMapper, Dept> implements DeptService {
}
