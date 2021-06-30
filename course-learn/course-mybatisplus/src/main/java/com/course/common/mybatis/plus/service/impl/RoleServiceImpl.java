package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.Role;
import com.course.common.mybatis.plus.mapper.RoleMapper;
import com.course.common.mybatis.plus.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
