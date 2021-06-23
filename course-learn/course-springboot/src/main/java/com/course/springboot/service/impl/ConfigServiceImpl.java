package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Config;
import com.course.springboot.mapper.ConfigMapper;
import com.course.springboot.service.ConfigService;

/**
 * service实现类：参数配置表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Service
public class ConfigServiceImpl extends UpServiceImpl<ConfigMapper, Config> implements ConfigService {
}
