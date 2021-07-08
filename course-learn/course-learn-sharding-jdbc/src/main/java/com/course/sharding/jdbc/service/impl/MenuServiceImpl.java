package com.course.sharding.jdbc.service.impl;

import org.springframework.stereotype.Service;

import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.sharding.jdbc.entity.Menu;
import com.course.sharding.jdbc.mapper.MenuMapper;
import com.course.sharding.jdbc.service.MenuService;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
public class MenuServiceImpl extends UpServiceImpl<MenuMapper, Menu> implements MenuService {

}
