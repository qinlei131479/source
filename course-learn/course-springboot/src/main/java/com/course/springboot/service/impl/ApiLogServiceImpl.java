package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.ApiLog;
import com.course.springboot.mapper.ApiLogMapper;
import com.course.springboot.service.ApiLogService;

/**
 * service实现类：接口日志表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Service
public class ApiLogServiceImpl extends UpServiceImpl<ApiLogMapper, ApiLog> implements ApiLogService {
}
