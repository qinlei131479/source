package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.springboot.entity.MenuApi;

/**
 * Mapper接口：菜单接口表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Mapper
public interface MenuApiMapper extends UpMapper<MenuApi> {
}
