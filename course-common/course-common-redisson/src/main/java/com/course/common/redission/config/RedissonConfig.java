package com.course.common.redission.config;

import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.common.redission.properties.RedissonProperties;

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
@ConditionalOnClass(RedissonProperties.class)
@RequiredArgsConstructor
public class RedissonConfig {
	/**
	 * 注入的redis属性
	 */
	private final RedissonProperties redissonProperties;
	private final static String REDIS_PREFIX = "redis://";

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout());
		RedissonProperties.Cluster cluster = null;
		RedissonProperties.Sentinel sentinel = null;
		if ((cluster = redissonProperties.getCluster()) != null && CollUtil.isNotEmpty(cluster.getNodes())) {
			// 集群模式
			config.useClusterServers().addNodeAddress(listToArray(cluster.getNodes()))
					.setPassword(redissonProperties.getPassword())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setScanInterval(redissonProperties.getScanInterval());

		} else if ((sentinel = redissonProperties.getSentinel()) != null && CollUtil.isNotEmpty(sentinel.getNodes())) {
			// 哨兵模式
			config.useSentinelServers().addSentinelAddress(listToArray(sentinel.getNodes()))
					.setCheckSentinelsList(false).setPassword(redissonProperties.getPassword())
					.setMasterName(sentinel.getMaster())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setDatabase(redissonProperties.getDatabase())
					.setScanInterval(redissonProperties.getScanInterval());
		} else {
			// 单机模式
			StringBuffer sb = new StringBuffer();
			sb.append(prefixAddress(redissonProperties.getHost())).append(":").append(redissonProperties.getPort());
			config.useSingleServer().setAddress(sb.toString()).setPassword(redissonProperties.getPassword())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setTimeout(redissonProperties.getTimeout()).setDatabase(redissonProperties.getDatabase());
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
		return list.stream().map(address -> prefixAddress(address)).toArray(item -> new String[list.size()]);
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
