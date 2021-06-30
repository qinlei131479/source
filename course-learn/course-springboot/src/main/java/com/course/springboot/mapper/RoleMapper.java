package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.springboot.entity.Role;

/**
 * Mapper接口：角色信息表
 *
 * @author qinlei
 * @date   2021/06/30 17:55
 */
@Mapper
public interface RoleMapper extends UpMapper<Role> {
}
