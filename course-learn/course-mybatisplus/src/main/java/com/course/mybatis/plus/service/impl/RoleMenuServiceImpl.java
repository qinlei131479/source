package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.RoleMenu;
import com.course.mybatis.plus.mapper.RoleMenuMapper;
import com.course.mybatis.plus.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
