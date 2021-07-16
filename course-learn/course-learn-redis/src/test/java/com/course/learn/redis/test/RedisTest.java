package com.course.learn.redis.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.course.common.cache.utils.RedisUtil;
import com.course.learn.redis.RedisApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/14 上午11:02
 */
@Slf4j
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

	@Resource
	private RedisUtil redisUtil;

	@Test
	void contextLoads() {
		String key = "test" + new Random().nextInt();
		redisUtil.set(key, "大人的");

		log.error("key= {},result = {}", key, redisUtil.get(key));

	}

	@Test
	public void testRedisSet() {
		log.error("start");
		// 记录执行开始时间
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			redisUtil.set("key" + i, "val" + i);
			redisUtil.del("key" + i);
		}
		// 记录执行结束时间
		long endTime = System.currentTimeMillis();
		log.error("执行耗时：" + (endTime - beginTime) + "毫秒");
	}

	@Test
	public void testPipelineSet() {

		// 记录执行开始时间
		long beginTime = System.currentTimeMillis();
		// 获取 Pipeline 对象
		Map map = new HashMap(100000);
		for (int i = 0; i < 100000; i++) {
			map.put("bbb" + i, "b");
		}
		redisUtil.executePipelined(map, 60);
		// 记录执行结束时间
		long endTime = System.currentTimeMillis();
		log.error("执行耗时：" + (endTime - beginTime) + "毫秒");
	}

}
