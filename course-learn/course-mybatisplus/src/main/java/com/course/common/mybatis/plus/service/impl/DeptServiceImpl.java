package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.Dept;
import com.course.common.mybatis.plus.mapper.DeptMapper;
import com.course.common.mybatis.plus.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
