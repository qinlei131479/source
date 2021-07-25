package com.course.biz.sys.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.biz.sys.entity.Menu;
import com.course.common.mybatis.mapper.UpMapper;

/**
 * Mapper接口：菜单表
 *
 * @author qinlei
 * @date 2021/06/28 15:35
 */
@Mapper
public interface MenuMapper extends UpMapper<Menu> {
}
