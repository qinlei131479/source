package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.Config;
import com.course.mybatis.plus.mapper.ConfigMapper;
import com.course.mybatis.plus.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
