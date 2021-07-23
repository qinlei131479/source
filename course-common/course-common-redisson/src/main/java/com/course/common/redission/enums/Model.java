package com.course.common.redission.enums;

/**
 * redis启动模式
 * 
 * @author qinlei
 * @date 2021/7/23 下午12:17
 */
public enum Model {
	// 哨兵
	SENTINEL,
	// 主从
	MASTERSLAVE,
	// 单例
	SINGLE,
	// 集群
	CLUSTER,
	// 云托管模式
	REPLICATED
}
