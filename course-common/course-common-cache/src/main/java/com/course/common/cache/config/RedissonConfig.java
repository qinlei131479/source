package com.course.common.cache.config;

import java.util.List;
import java.util.stream.Collectors;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.common.cache.properties.RedisProperties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * redisson客户端注入
 * 
 * @author qinlei
 * @date 2021/7/18 下午3:18
 */
@Configuration
@ConditionalOnClass(RedisProperties.class)
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
		config.setLockWatchdogTimeout(redisProperties.getLockWatchdogTimeout());
		RedisProperties.Cluster cluster = null;
		RedisProperties.Sentinel sentinel = null;
		if ((cluster = redisProperties.getCluster()) != null && CollUtil.isNotEmpty(cluster.getNodes())) {
			// 集群模式
			config.useClusterServers().addNodeAddress(listToArray(cluster.getNodes()))
					.setPassword(redisProperties.getPassword())
					.setPingConnectionInterval(redisProperties.getConnectTimeout())
					.setScanInterval(redisProperties.getScanInterval());

		} else if ((sentinel = redisProperties.getSentinel()) != null && CollUtil.isNotEmpty(sentinel.getNodes())) {
			// 哨兵模式
			config.useSentinelServers().addSentinelAddress(listToArray(sentinel.getNodes()))
					.setCheckSentinelsList(false).setPassword(redisProperties.getPassword())
					.setMasterName(sentinel.getMaster()).setPingConnectionInterval(redisProperties.getConnectTimeout())
					.setDatabase(redisProperties.getDatabase()).setScanInterval(redisProperties.getScanInterval());
		} else {
			// 单机模式
			StringBuffer sb = new StringBuffer();
			sb.append(prefixAddress(redisProperties.getHost())).append(":").append(redisProperties.getPort());
			config.useSingleServer().setAddress(sb.toString()).setPassword(redisProperties.getPassword())
					.setPingConnectionInterval(redisProperties.getConnectTimeout())
					.setTimeout(redisProperties.getTimeout())
					.setDatabase(redisProperties.getDatabase());
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
		List<String> doneList = list.stream().map(address -> prefixAddress(address)).collect(Collectors.toList());
		return doneList.toArray(new String[doneList.size()]);
	}

	/**
	 * redis服务地址补充redis://
	 * 
	 * @param address
	 * @return
	 */
	private String prefixAddress(String address) {
		if (StrUtil.isNotBlank(address) && !address.startsWith(REDIS_PREFIX)) {
			return REDIS_PREFIX + address;
		}
		return address;
	}
}
