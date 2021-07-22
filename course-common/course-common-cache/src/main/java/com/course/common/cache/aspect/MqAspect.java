package com.course.common.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import com.course.common.cache.annotation.MqPublish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/22 下午9:43
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class MqAspect {

	private final RedissonClient redissonClient;

	@Pointcut("@annotation(mq)")
	public void aspect(MqPublish mq) {
	}

	@Around("aspect(mq)")
	public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, MqPublish mq) {
		try {
			Object obj = proceedingJoinPoint.proceed();
			RTopic topic = redissonClient.getTopic(mq.name());
			topic.publish(obj);
			return obj;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
