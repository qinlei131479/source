package com.course.common.cache.config;

import java.util.List;
import java.util.stream.Collectors;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.common.cache.properties.RedisProperties;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;

/**
 * redisson客户端注入
 * 
 * @author qinlei
 * @date 2021/7/18 下午3:18
 */
@Configuration
@RequiredArgsConstructor
public class RedissonConfig {
	/**
	 * 注入的redis属性
	 */
	private final RedisProperties redisProperties;
	private final static String REDIS_PREFIX = "redis://";

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		RedisProperties.Cluster cluster = null;
		RedisProperties.Sentinel sentinel = null;
		// 单机模式
		Integer database = redisProperties.getDatabase() == null ? 0 : redisProperties.getDatabase();
		if ((cluster = redisProperties.getCluster()) != null && CollUtil.isNotEmpty(cluster.getNodes())) {
			config.useClusterServers().addNodeAddress(listToArray(cluster.getNodes()))
					.setPassword(redisProperties.getPassword()).setScanInterval(5000);

		} else if ((sentinel = redisProperties.getSentinel()) != null && CollUtil.isNotEmpty(sentinel.getNodes())) {
			// 哨兵模式
			config.useSentinelServers().addSentinelAddress(listToArray(sentinel.getNodes()))
					.setCheckSentinelsList(false).setPassword(redisProperties.getPassword())
					.setMasterName(sentinel.getMaster()).setDatabase(database);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(REDIS_PREFIX).append(redisProperties.getHost()).append(":").append(redisProperties.getPort());
			config.useSingleServer().setAddress(sb.toString()).setPassword(redisProperties.getPassword())
					.setDatabase(database);
		}
		return Redisson.create(config);
	}

	/**
	 * 集合转换为数组
	 * 
	 * @param list
	 * @return
	 */
	private String[] listToArray(List<String> list) {
		List<String> doneList = list.stream().map(item -> item = REDIS_PREFIX + item).collect(Collectors.toList());
		return doneList.toArray(new String[doneList.size()]);
	}
}
