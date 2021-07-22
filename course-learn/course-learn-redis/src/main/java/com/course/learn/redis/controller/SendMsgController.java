package com.course.learn.redis.controller;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.cache.RedisTopic;
import com.course.common.cache.annotation.MqPublish;
import com.course.learn.redis.entity.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/22 下午10:00
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SendMsgController {

	private final RedissonClient redissonClient;

	/**
	 * 发送方式一: 使用代码发送消息
	 * 
	 * @param name
	 * @param val
	 * @return
	 */
	@GetMapping("/send/{name}/{val}")
	public Object notice(@PathVariable String name, @PathVariable String val) {
		RTopic topic = redissonClient.getTopic(RedisTopic.redisson_topic);
		topic.publish(new Notice(name, val));
		return "success";
	}

	/**
	 * 发送方式二:使用注解发送消息, 返回值就是需要发送的消息
	 * 
	 * @param name
	 * @param val
	 * @return
	 */
	@GetMapping("/send2/{name}/{val}")
	@MqPublish(name = RedisTopic.redisson_topic)
	public Notice notice2(@PathVariable String name, @PathVariable String val) {
		return new Notice(name, val);
	}

	@GetMapping("/send3/{name}/{val}")
	@MqPublish(name = RedisTopic.redisson_pattern)
	public Notice notice3(@PathVariable String name, @PathVariable String val) {
		return new Notice(name, val);
	}

}
