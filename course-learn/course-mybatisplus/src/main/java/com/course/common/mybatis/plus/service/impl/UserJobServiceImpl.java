package com.course.common.mybatis.plus.service.impl;

import com.course.common.mybatis.plus.entity.UserJob;
import com.course.common.mybatis.plus.mapper.UserJobMapper;
import com.course.common.mybatis.plus.service.IUserJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class UserJobServiceImpl extends ServiceImpl<UserJobMapper, UserJob> implements IUserJobService {

}
