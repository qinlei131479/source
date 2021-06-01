package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.UserRole;
import com.course.mybatis.plus.mapper.UserRoleMapper;
import com.course.mybatis.plus.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
