package com.course.biz.sys.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.biz.sys.entity.Api;

/**
 * Mapper接口：应用接口表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Mapper
public interface ApiMapper extends UpMapper<Api> {
}
