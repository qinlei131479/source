package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.Menu;
import com.course.mybatis.plus.mapper.MenuMapper;
import com.course.mybatis.plus.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
