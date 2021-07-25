package com.course.springboot.controller;

import java.util.concurrent.TimeUnit;

import com.course.common.core.entity.Res;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.cache.RedisTopic;
import com.course.common.cache.utils.RedisUtil;
import com.course.common.redission.CommonTopic;
import com.course.common.redission.annotation.Lock;
import com.course.common.redission.annotation.MqPublish;
import com.course.springboot.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/24 下午6:52
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {

	private final RedisUtil redisUtil;
	private final RedissonClient redissonClient;
	private final static String LOCK_KEY = "lock_key_";

	@GetMapping("/user/notice")
	public Res noticeUser(User user) {
		redisUtil.convertAndSend(RedisTopic.topic, user);
		return Res.succ();
	}

	/**
	 * 发送方式一: 使用代码发送消息
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/user/send")
	public Res notice(User user) {
		RTopic topic = redissonClient.getTopic(CommonTopic.redisson_topic);
		topic.publish(user);
		return Res.succ();
	}

	/**
	 * 发送方式二:使用注解发送消息, 返回值就是需要发送的消息
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/user/send2")
	@MqPublish(name = CommonTopic.redisson_topic)
	public Res notice2(User user) {
		user.setCreateBy("ql annotion");
		return Res.succ(user);
	}

	@GetMapping("/user/send3")
	@MqPublish(name = CommonTopic.redisson_pattern)
	public Res notice3(User user) {
		user.setCreateBy("ql");
		return Res.succ(user);
	}

	// **************************锁*****************************************

	@GetMapping("/notice/lock")
	public Res lock(@PathVariable Long id) throws InterruptedException {
		String key = LOCK_KEY + id;
		RLock rLock = redissonClient.getLock(key);
		boolean isLock = rLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
		if (isLock) {
			log.error("rLock is success");
			Thread.sleep(10000);
		}
		rLock.unlock();
		return Res.succ();
	}

	@GetMapping("/notice/lock2")
	@Lock(keys = "#user.name", keyConstant = "|")
	public Res lock2(User user) throws InterruptedException {
		log.error("rLock2 is success,{}", user.getName());
		Thread.sleep(5000);
		return Res.succ();
	}

	@GetMapping("/notice/lock3")
	@Lock(keys = { "#user.name", "tesssss" })
	public Res lock3(User user) throws InterruptedException {
		log.error("rLock3 is success,{}", user.getName());
		Thread.sleep(5000);
		return Res.succ();
	}
}
