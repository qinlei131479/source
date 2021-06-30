package com.course.common.mybatis.resource.executor;

import java.util.List;

import com.course.common.mybatis.resource.config.Configuration;

/**
 * 用来处理二级缓存
 * 
 * @author qinlei
 * @date 2021/6/1 上午11:20
 */
public class CachingExecutor implements Executor {

	private Executor delegate;

	public CachingExecutor(Executor delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public <T> List<T> query(String statementId, Configuration configuration, Object param) {
		// TODO 如果开启了二级缓存，那从二级缓存中获取数据

		// 如果没有开启二级缓存，则走一级缓存（不管是批处理的、简单的、重用的）
		return delegate.query(statementId, configuration, param);
	}
}
