package com.course.common.cache.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
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
	private Integer database = 0;
	/**
	 * 单机host
	 */
	private String host;
	/**
	 * 单机端口
	 */
	private Integer port = 6379;

	/**
	 * 超时（redisson超时报错）
	 */
	private Integer connectTimeout = 1000;

	/**
	 * 扫描（redisson超时报错）
	 */
	private Integer scanInterval = 5000;

	/**
	 * redis集群，必须new对象，否则无法初始化
	 */
	@NestedConfigurationProperty
	private Cluster cluster = new Cluster();
	/**
	 * 哨兵配置，必须new对象，否则无法初始化
	 */
	@NestedConfigurationProperty
	private Sentinel sentinel = new Sentinel();

	@Data
	public class Cluster {

		private List<String> nodes;
		private Integer maxRedirects;

	}

	@Data
	public class Sentinel {

		private String master;
		private List<String> nodes;

	}
}
