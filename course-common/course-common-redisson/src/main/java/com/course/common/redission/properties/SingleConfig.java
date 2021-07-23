package com.course.common.redission.properties;

import lombok.Data;

/**
 * 单节点配置
 * 
 * @author qinlei
 * @date 2021/7/23 上午10:48
 */
@Data
public class SingleConfig {
	/**
	 * 服务器地址,必填ip:port
	 */
	private String address;
	/**
	 * 最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度。
	 */
	private Integer connectionMinIdleSize = 32;
	/**
	 * 连接池最大容量。连接池的连接数量自动弹性伸缩。
	 */
	private Integer connectionPoolSize = 64;

}
