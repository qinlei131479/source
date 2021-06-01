package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.Quartz;
import com.course.mybatis.plus.mapper.QuartzMapper;
import com.course.mybatis.plus.service.IQuartzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Service
public class QuartzServiceImpl extends ServiceImpl<QuartzMapper, Quartz> implements IQuartzService {

}
