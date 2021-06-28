package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.MenuApi;
import com.course.springboot.mapper.MenuApiMapper;
import com.course.springboot.service.MenuApiService;

/**
 * service实现类：菜单接口表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Service
public class MenuApiServiceImpl extends UpServiceImpl<MenuApiMapper, MenuApi> implements MenuApiService {
}
