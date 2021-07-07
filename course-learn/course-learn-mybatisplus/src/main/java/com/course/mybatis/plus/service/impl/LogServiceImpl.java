package com.course.mybatis.plus.service.impl;

import com.course.mybatis.plus.entity.Log;
import com.course.mybatis.plus.mapper.LogMapper;
import com.course.mybatis.plus.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
