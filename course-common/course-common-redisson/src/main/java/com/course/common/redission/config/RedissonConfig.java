package com.course.common.redission.config;

import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.common.redission.properties.MultipleConfig;
import com.course.common.redission.properties.RedissonProperties;
import com.course.common.redission.properties.SingleConfig;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * redisson客户端注入
 * 
 * @author qinlei
 * @date 2021/7/18 下午3:18
 */
@Slf4j
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
		config.setCodec(ReflectUtil.newInstance(redissonProperties.getCodec()));
		if (redissonProperties.getThreads() != null) {
			config.setThreads(redissonProperties.getThreads());
		}
		if (redissonProperties.getNettyThreads() != null) {
			config.setNettyThreads(redissonProperties.getNettyThreads());
		}
		MultipleConfig multipleConfig = redissonProperties.getMultipleConfig();
		switch (redissonProperties.getModel()) {
		// 哨兵模式
		case SENTINEL:
			config.useSentinelServers().addSentinelAddress(listToArray(multipleConfig.getNodes()))
					.setCheckSentinelsList(false).setPassword(redissonProperties.getPassword())
					.setMasterName(multipleConfig.getMasterName()).setDatabase(redissonProperties.getDatabase())
					.setRetryAttempts(redissonProperties.getRetryAttempts())
					.setRetryInterval(redissonProperties.getRetryInterval())
					.setDnsMonitoringInterval(redissonProperties.getDnsMonitorInterval())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinIdleSize())
					.setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
					.setScanInterval(multipleConfig.getScanInterval());

			break;
		// 集群模式
		case CLUSTER:
			config.useClusterServers().addNodeAddress(listToArray(multipleConfig.getNodes()))
					.setPassword(redissonProperties.getPassword())
					.setRetryAttempts(redissonProperties.getRetryAttempts())
					.setRetryInterval(redissonProperties.getRetryInterval())
					.setDnsMonitoringInterval(redissonProperties.getDnsMonitorInterval())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinIdleSize())
					.setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
					.setReadMode(multipleConfig.getReadMode()).setSubscriptionMode(multipleConfig.getSubscriptionMode())
					.setLoadBalancer(ReflectUtil.newInstance(multipleConfig.getLoadBalancer()))
					.setScanInterval(multipleConfig.getScanInterval()).setTimeout(redissonProperties.getTimeout());

			break;
		// 云托管模式
		case REPLICATED:
			config.useReplicatedServers().addNodeAddress(listToArray(multipleConfig.getNodes()))
					.setPassword(redissonProperties.getPassword())
					.setRetryAttempts(redissonProperties.getRetryAttempts())
					.setRetryInterval(redissonProperties.getRetryInterval())
					.setDnsMonitoringInterval(redissonProperties.getDnsMonitorInterval())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinIdleSize())
					.setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
					.setReadMode(multipleConfig.getReadMode()).setSubscriptionMode(multipleConfig.getSubscriptionMode())
					.setLoadBalancer(ReflectUtil.newInstance(multipleConfig.getLoadBalancer()))
					.setScanInterval(multipleConfig.getScanInterval()).setTimeout(redissonProperties.getTimeout());
			break;
		// 单机模式
		default:
			SingleConfig singleConfig = redissonProperties.getSingleConfig();
			config.useSingleServer().setAddress(prefixAddress(singleConfig.getAddress()))
					.setPassword(redissonProperties.getPassword()).setDatabase(redissonProperties.getDatabase())
					.setConnectionMinimumIdleSize(singleConfig.getConnectionMinIdleSize())
					.setConnectionPoolSize(singleConfig.getConnectionPoolSize())
					.setRetryAttempts(redissonProperties.getRetryAttempts())
					.setRetryInterval(redissonProperties.getRetryInterval())
					.setDnsMonitoringInterval(redissonProperties.getDnsMonitorInterval())
					.setPingConnectionInterval(redissonProperties.getConnectTimeout())
					.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinIdleSize())
					.setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
					.setTimeout(redissonProperties.getTimeout());
		}
		log.info("redisson mode = {}", redissonProperties.getModel());
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
