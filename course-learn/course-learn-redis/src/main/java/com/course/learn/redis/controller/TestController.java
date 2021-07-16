package com.course.learn.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.cache.RedisTopic;
import com.course.common.cache.utils.RedisUtil;
import com.course.learn.redis.entity.Notice;

import lombok.RequiredArgsConstructor;

/**
 * @author qinlei
 * @date 2021/7/16 下午10:25
 */
@RestController
@RequiredArgsConstructor
public class TestController {

	private final RedisUtil redisUtil;

	@GetMapping("/notice/{name}/{val}")
	public Object notice(@PathVariable String name, @PathVariable String val) {
		redisUtil.convertAndSend(RedisTopic.topic, new Notice(name, val));
		return "success";
	}
}
