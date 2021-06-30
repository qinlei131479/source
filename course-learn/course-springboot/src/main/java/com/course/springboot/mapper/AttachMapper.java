package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.springboot.entity.Attach;

/**
 * Mapper接口：附件表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Mapper
public interface AttachMapper extends UpMapper<Attach> {
}
