package com.course.learn.redis.test.config;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import lombok.extern.slf4j.Slf4j;

/**
 * redis分布式锁
 * 
 * @author qinlei
 * @date 2021/7/14 下午9:21
 */
@Slf4j
public class DistributedRedisLock {
	private static RedissonClient redisson = RedissonConfig.getRedisClient();
	/**
	 * 加锁
	 */
	private static final String LOCK_TITLE = "redisLock_";

	/**
	 * 获取锁
	 * 
	 * @param lockName
	 * @return
	 */
	public static boolean acquire(String lockName) throws InterruptedException {
		log.error("myLock acquire");
		// 获取锁对象
		String key = LOCK_TITLE + lockName;
		// 加锁，并且设置锁过期时间3秒，防止死锁的产生
		RLock myLock = redisson.getLock(key);
		boolean isLock = myLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
		if (isLock) {
			log.error("myLock is success");
			Thread.sleep(5000);
		}
		return isLock;
	}

	/**
	 * 解锁
	 * 
	 * @param lockName
	 */
	public static void release(String lockName) {
		// 必须是和加锁时的同一个key
		String key = LOCK_TITLE + lockName;
		// 获取锁对象
		RLock mylock = redisson.getLock(key);
		if (mylock != null) {
			// 释放锁(解锁)
			mylock.unlock();
			log.error("myLock is release");
		}
	}
}
