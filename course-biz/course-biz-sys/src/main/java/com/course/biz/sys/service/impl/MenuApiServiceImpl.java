package com.course.biz.sys.service.impl;

import org.springframework.stereotype.Service;

import com.course.biz.sys.entity.MenuApi;
import com.course.biz.sys.mapper.MenuApiMapper;
import com.course.biz.sys.service.MenuApiService;
import com.course.common.mybatis.service.impl.UpServiceImpl;

/**
 * service实现类：菜单接口表
 *
 * @author qinlei
 * @date 2021/06/28 15:35
 */
@Service
public class MenuApiServiceImpl extends UpServiceImpl<MenuApiMapper, MenuApi> implements MenuApiService {
}
