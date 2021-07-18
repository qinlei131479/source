package com.course.learn.redis.test;

import com.course.learn.redis.test.config.DistributedRedisLock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/14 下午9:28
 */
@Slf4j
public class RedisLockTest {

	public static void main(String[] args) throws InterruptedException {
		String key = "kkb";
		// 加锁
		boolean success = DistributedRedisLock.acquire(key);
		if (success) {
			log.error("success key=" + key);
			// 执行具体业务逻辑
			// Thread.sleep(10000);
			// 释放锁
			DistributedRedisLock.release(key);
			success = false;
		}
	}
}
