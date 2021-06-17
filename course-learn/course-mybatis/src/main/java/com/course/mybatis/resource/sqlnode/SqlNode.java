package com.course.mybatis.resource.sqlnode;

/**
 * 接口是提供功能的
 * 
 * @author qinlei
 * @date 2021/5/31 下午4:01
 */
public interface SqlNode {

	void apply(DynamicContext context);
}
