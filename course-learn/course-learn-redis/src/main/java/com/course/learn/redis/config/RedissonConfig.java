package com.course.learn.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 客户端连接配置
 * 
 * @author qinlei
 * @date 2021/7/14 下午9:17
 */
public class RedissonConfig {
	/**
	 * 声明redisso对象
	 */

	private static volatile RedissonClient redisson = null;

	private RedissonConfig() {

	}

	static {
		// 集群状态扫描间隔时间，单位是毫秒
		// cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
		// config.useClusterServers().setScanInterval(2000).addNodeAddress("redis://172.24.59.177:6800")
		// .addNodeAddress("redis://47.100.173.179:6801").addNodeAddress("redis://47.100.173.179:6802")
		// .addNodeAddress("redis://121.89.200.198:6800").addNodeAddress("redis://121.89.200.198:6801")
		// .addNodeAddress("redis://121.89.200.198:6802").setPassword("ql1234");
		// redisson = (Redisson) Redisson.create(config);

	}

	/**
	 * 获取redisson对象的方法
	 * 
	 * @return
	 */
	public static RedissonClient getRedisClient() {
		if (redisson == null) {
			synchronized (RedissonClient.class) {
				if (redisson == null) {
					redisson = buildClientCluster();
				}
			}
		}
		return redisson;
	}

	/**
	 * 构建单机redis
	 * 
	 * @return
	 */
	public static RedissonClient buildClient() {
		Config config = new Config();
		// 单机模式
		config.useSingleServer().setAddress("redis://47.100.173.179:6379").setPassword("ql1234").setDatabase(0);
		return Redisson.create(config);
	}

	/**
	 * 构建哨兵redis
	 *
	 * @return
	 */
	public static RedissonClient buildClientSentinel() {
		Config config = new Config();
		// 哨兵模式
		config.useSentinelServers()
				.addSentinelAddress("redis://47.100.173.179:26379", "redis://47.100.173.179:26378",
						"redis://47.100.173.179:26377")
				.setCheckSentinelsList(false).setPassword("ql1234").setMasterName("mymaster").setDatabase(0);
		return Redisson.create(config);
	}

	/**
	 * 构建集群redis
	 *
	 * @return
	 */
	public static RedissonClient buildClientCluster() {
		Config config = new Config();
		// 集群模式
		config.useClusterServers()
				.addNodeAddress("redis://47.100.173.179:6800", "redis://47.100.173.179:6801",
						"redis://47.100.173.179:6802", "redis://121.89.200.198:6800", "redis://121.89.200.198:6801",
						"redis://121.89.200.198:6802")
				.setPassword("ql1234").setScanInterval(5000);
		return Redisson.create(config);
	}
}
