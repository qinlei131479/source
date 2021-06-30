package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Attach;
import com.course.springboot.mapper.AttachMapper;
import com.course.springboot.service.AttachService;

/**
 * service实现类：附件表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Service
public class AttachServiceImpl extends UpServiceImpl<AttachMapper, Attach> implements AttachService {
}
