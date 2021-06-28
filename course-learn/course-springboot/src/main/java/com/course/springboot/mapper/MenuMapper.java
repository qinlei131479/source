package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.mybatis.mapper.UpMapper;
import com.course.springboot.entity.Menu;

/**
 * Mapper接口：菜单表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Mapper
public interface MenuMapper extends UpMapper<Menu> {
}
