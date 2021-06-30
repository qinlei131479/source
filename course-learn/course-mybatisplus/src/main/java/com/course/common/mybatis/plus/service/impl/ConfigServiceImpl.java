package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.Config;
import com.course.common.mybatis.plus.mapper.ConfigMapper;
import com.course.common.mybatis.plus.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
