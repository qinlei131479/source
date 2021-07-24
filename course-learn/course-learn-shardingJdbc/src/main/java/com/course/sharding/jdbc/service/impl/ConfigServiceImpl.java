package com.course.sharding.jdbc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.course.common.core.entity.Res;
import org.springframework.stereotype.Service;

import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.sharding.jdbc.entity.Config;
import com.course.sharding.jdbc.mapper.ConfigMapper;
import com.course.sharding.jdbc.service.ConfigService;

/**
 * service实现类：
 *
 * @author qinlei
 * @date   2021/07/08 21:40
 */
@Service
public class ConfigServiceImpl extends UpServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public Res<?> create(Config req) {
        req.setId(IdWorker.getId());
        req.setRemark("test");
        return super.create(req);
    }
}
