package com.course.biz.sys.service.impl;

import org.springframework.stereotype.Service;

import com.course.biz.sys.entity.Menu;
import com.course.biz.sys.mapper.MenuMapper;
import com.course.biz.sys.service.MenuService;
import com.course.common.mybatis.service.impl.UpServiceImpl;

/**
 * service实现类：菜单表
 *
 * @author qinlei
 * @date 2021/06/28 15:35
 */
@Service
public class MenuServiceImpl extends UpServiceImpl<MenuMapper, Menu> implements MenuService {
}
