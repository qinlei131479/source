package com.course.learn.redis.controller;

import java.util.concurrent.TimeUnit;

import com.course.common.redission.annotation.Lock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.cache.RedisTopic;
import com.course.common.cache.utils.RedisUtil;
import com.course.learn.redis.entity.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/16 下午10:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

	private final RedisUtil redisUtil;
	private final RedissonClient redissonClient;
	private final static String LOCK_KEY = "lock_key_";

	@GetMapping("/notice/{name}/{val}")
	public Object notice(@PathVariable String name, @PathVariable String val) {
		redisUtil.convertAndSend(RedisTopic.topic, new Notice(name, val));
		return "success";
	}

	@GetMapping("/notice/lock/{id}")
	public Object lock(@PathVariable Long id) throws InterruptedException {
		String key = LOCK_KEY + id;
		RLock rLock = redissonClient.getLock(key);
		boolean isLock = rLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
		if (isLock) {
			log.error("rLock is success");
			Thread.sleep(10000);
		}
		rLock.unlock();
		return "success lock";
	}

	@GetMapping("/notice/lock2")
	@Lock(keys = "#notice.name", keyConstant = "|")
	public Object lock2(Notice notice) throws InterruptedException {
		log.error("rLock2 is success,{}", notice.getName());
		Thread.sleep(5000);
		return "success2 lock";
	}

	@GetMapping("/notice/lock3")
	@Lock(keys = {"#notice.name","tesssss"})
	public Object lock3(Notice notice) throws InterruptedException {
		log.error("rLock3 is success,{}", notice.getName());
		Thread.sleep(5000);
		return "success3 lock";
	}
}
