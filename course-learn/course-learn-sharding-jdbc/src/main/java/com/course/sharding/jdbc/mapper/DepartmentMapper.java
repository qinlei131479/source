package com.course.sharding.jdbc.mapper;

import java.util.List;

import com.course.sharding.jdbc.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    List<Department> listByParam(@Param("name") String name);
}
