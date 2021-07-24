package com.course.sharding.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.sharding.jdbc.entity.Menu;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Mapper
public interface MenuMapper extends UpMapper<Menu> {

}
