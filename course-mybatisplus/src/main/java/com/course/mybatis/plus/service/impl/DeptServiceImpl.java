package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.Dept;
import com.course.mybatis.plus.mapper.DeptMapper;
import com.course.mybatis.plus.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
