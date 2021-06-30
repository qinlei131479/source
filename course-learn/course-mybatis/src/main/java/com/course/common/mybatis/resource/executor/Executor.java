package com.course.common.mybatis.resource.executor;

import java.util.List;

import com.course.common.mybatis.resource.config.Configuration;

/**
 * 将不同的执行器抽象化
 * 
 * @author qinlei
 * @date 2021/6/1 上午11:15
 */
public interface Executor {

	<T> List<T> query(String statementId, Configuration configuration, Object param);
}
