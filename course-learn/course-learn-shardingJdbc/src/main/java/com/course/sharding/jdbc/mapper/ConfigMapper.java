package com.course.sharding.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.sharding.jdbc.entity.Config;

/**
 * Mapper接口：
 *
 * @author qinlei
 * @date   2021/07/08 21:40
 */
@Mapper
public interface ConfigMapper extends UpMapper<Config> {
}
