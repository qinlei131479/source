package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.Job;
import com.course.common.mybatis.plus.mapper.JobMapper;
import com.course.common.mybatis.plus.service.IJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

}