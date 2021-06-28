package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.mybatis.mapper.UpMapper;
import com.course.springboot.entity.Api;

/**
 * Mapper接口：应用接口表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Mapper
public interface ApiMapper extends UpMapper<Api> {
}
