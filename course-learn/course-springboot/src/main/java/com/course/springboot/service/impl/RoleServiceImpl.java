package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Role;
import com.course.springboot.mapper.RoleMapper;
import com.course.springboot.service.RoleService;

/**
 * service实现类：角色信息表
 *
 * @author qinlei
 * @date   2021/06/30 17:55
 */
@Service
public class RoleServiceImpl extends UpServiceImpl<RoleMapper, Role> implements RoleService {
}
