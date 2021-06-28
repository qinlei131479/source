package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Menu;
import com.course.springboot.mapper.MenuMapper;
import com.course.springboot.service.MenuService;

/**
 * service实现类：菜单表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Service
public class MenuServiceImpl extends UpServiceImpl<MenuMapper, Menu> implements MenuService {
}
