package com.course.common.cache.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 获取redis注入属性，方便配置，也可以配置redisson
 * 
 * @author qinlei
 * @date 2021/7/18 下午3:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 使用redis库下标
	 */
	private Integer database;
	/**
	 * 单机host
	 */
	private String host;
	/**
	 * 单机端口
	 */
	private Integer port;

	/**
	 * 超时（redisson超时报错）
	 */
	private Integer connectTimeout;

	/**
	 * redis集群，必须new对象，否则无法初始化
	 */
	private Cluster cluster = new Cluster();
	/**
	 * 哨兵配置，必须new对象，否则无法初始化
	 */
	private Sentinel sentinel = new Sentinel();

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "spring.redis.cluster")
	public class Cluster {

		private List<String> nodes;
		private Integer maxRedirects;

	}

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "spring.redis.sentinel")
	public class Sentinel {

		private String master;
		private List<String> nodes;

	}
}
