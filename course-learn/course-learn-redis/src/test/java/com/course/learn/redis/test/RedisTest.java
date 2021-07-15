package com.course.learn.redis.test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import redis.clients.jedis.*;

/**
 * @author qinlei
 * @date 2021/7/14 上午11:02
 */
public class RedisTest {

	public static JedisCluster config() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大连接数
		poolConfig.setMaxTotal(100);
		// 最大空闲数
		poolConfig.setMaxIdle(10);
		// 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
		// Could not get a resource from the pool
		poolConfig.setMaxWaitMillis(1000);
		Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();

		nodes.add(new HostAndPort("47.100.173.179", 6800));
		nodes.add(new HostAndPort("47.100.173.179", 6801));
		nodes.add(new HostAndPort("47.100.173.179", 6802));
		nodes.add(new HostAndPort("121.89.200.198", 6800));
		nodes.add(new HostAndPort("121.89.200.198", 6801));
		nodes.add(new HostAndPort("121.89.200.198", 6802));
		return new JedisCluster(nodes, 3000, 3000, 5, "ql1234", poolConfig);
	}

	public static void testRedisSet() {
		JedisCluster cluster = config();
		// 记录执行开始时间
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			cluster.set("key" + i, "val" + i);
			cluster.del("key" + i);
		}
		// 记录执行结束时间
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时：" + (endTime - beginTime) + "毫秒");
	}

	public static void testPipelineSet() {
		Jedis jedis = new Jedis("47.100.173.179", 6800);
		jedis.auth("ql1234");
		// 记录执行开始时间
		long beginTime = System.currentTimeMillis();
		// 获取 Pipeline 对象
		Pipeline pipe = jedis.pipelined();
		// 设置多个 Redis 命令
		for (int i = 0; i < 10000; i++) {
			pipe.set("key" + i, "val" + i);
			pipe.del("key" + i);
		}
		// 执行命令
		pipe.sync();
		// 记录执行结束时间
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时：" + (endTime - beginTime) + "毫秒");
	}

	public static void main(String[] args) {
		ArrayList<RedisURI> list = new ArrayList<>();
		list.add(RedisURI.create("redis://192.168.37.128:7000"));
		list.add(RedisURI.create("redis://192.168.37.128:7001"));
		list.add(RedisURI.create("redis://192.168.37.128:7002"));
		list.add(RedisURI.create("redis://192.168.37.128:7003"));
		list.add(RedisURI.create("redis://192.168.37.128:7004"));
		list.add(RedisURI.create("redis://192.168.37.128:7005"));
		RedisClusterClient client = RedisClusterClient.create(list);
		// RedisClusterClient client =
		// RedisClusterClient.create("redis://192.168.37.128:7000");
		StatefulRedisClusterConnection<String, String> connect = client.connect();

		/* 同步执行的命令 */
		RedisAdvancedClusterCommands<String, String> commands = connect.sync();
		String str = commands.get("test2");
		System.out.println(str);

		/* 异步执行的命令 */
		// RedisAdvancedClusterAsyncCommands<String, String> commands=
		// connect.async();
		// RedisFuture<String> future = commands.get("test2");
		// try {
		// String str = future.get();
		// System.out.println(str);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }

		connect.close();
		client.shutdown();
	}
}
