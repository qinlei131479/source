package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.mybatis.mapper.UpMapper;
import com.course.springboot.entity.ApiLog;

/**
 * Mapper接口：接口日志表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Mapper
public interface ApiLogMapper extends UpMapper<ApiLog> {
}
