package com.course.biz.sys.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.biz.sys.entity.Config;

/**
 * Mapper接口：参数配置表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Mapper
public interface ConfigMapper extends UpMapper<Config> {
}
