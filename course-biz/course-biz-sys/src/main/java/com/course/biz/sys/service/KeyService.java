package com.course.biz.sys.service;

import com.course.biz.sys.entity.Key;
import com.course.common.mybatis.service.UpService;

/**
 * @author qinlei
 * @date 2021/6/25 下午5:47
 */
public interface KeyService extends UpService<Key> {

	/**
	 * 查询下一个id（首先从缓存获取）
	 *
	 * @return
	 */
	Long updateKey();

	/**
	 * 准备主键（定时任务调用）
	 *
	 * @return
	 */
	Long prepare(Key req);
}
