package com.course.common.cache.aspect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.course.common.cache.annotation.Lock;
import com.course.common.cache.enums.LockModel;
import com.course.common.cache.exception.LockException;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 锁注解切面
 * 
 * @author qinlei
 * @date 2021/7/21 下午11:00
 */
@Slf4j
@Aspect
@Order(-10)
@Component
@RequiredArgsConstructor
public class LockAspect {

	private final RedissonClient redissonClient;
	private final static String REDIS_LOCK_PREFIX = "redisson:lock:";

	@Pointcut("@annotation(lock)")
	public void doLockAspect(Lock lock) {
	}

	/**
	 * 
	 * @param proceedingJoinPoint
	 * @param lock
	 * @return
	 * @throws Throwable
	 */
	@Around("doLockAspect(lock)")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint, Lock lock) throws Throwable {
		String[] parameterNames = new LocalVariableTableParameterNameDiscoverer()
				.getParameterNames(((MethodSignature) proceedingJoinPoint.getSignature()).getMethod());
		Object[] args = proceedingJoinPoint.getArgs();
		// 获取全部锁的key值
		List<String> keyList = getKeyListBySpel(lock.keys(), parameterNames, args, lock.keyConstant());
		RLock rLock = buildRLock(lock.lockModel(), keyList);
		if (rLock == null) {
			throw new LockException("获取锁失败");
		}
		boolean success = false;
		try {
			if (lock.waitTime() == -1) {
				// 一直等待加锁
				success = true;
				rLock.lock(lock.leaseTime(), lock.unit());
			} else {
				success = rLock.tryLock(lock.waitTime(), lock.leaseTime(), lock.unit());
			}
			if (success) {
				return proceedingJoinPoint.proceed();
			}
			throw new LockException("获取锁失败");
		} finally {
			RLock unLock = buildRLock(lock.lockModel(), keyList);
			log.error("unlock = {}", unLock);
			if (success && unLock != null) {
				unLock.unlock();
			}
		}
	}

	/**
	 * 解析锁，并获取真正的锁对象
	 * 
	 * @param lockModel
	 * @return
	 */
	private RLock buildRLock(LockModel lockModel, List<String> keyList) {
		if (CollUtil.isEmpty(keyList)) {
			return null;
		}
		// 判断锁模式，多参数时为红锁模式
		if (lockModel.equals(LockModel.AUTO)) {
			lockModel = keyList.size() > 1 ? LockModel.REDLOCK : LockModel.REENTRANT;
		}
		// 红锁、联锁生成多个锁
		RLock[] rLocks = null;
		if (lockModel.equals(LockModel.REDLOCK) || lockModel.equals(LockModel.MULTIPLE)) {
			rLocks = keyList.stream().map(key -> redissonClient.getLock(key))
					.toArray(lock -> new RLock[keyList.size()]);
		}
		RLock rLock = null;
		switch (lockModel) {
		// 公平锁,只获取第一个值
		case FAIR:
			rLock = redissonClient.getFairLock(keyList.get(0));
			break;
		// 红锁
		case REDLOCK:
			rLock = new RedissonRedLock(rLocks);
			break;
		// 联锁
		case MULTIPLE:
			rLock = new RedissonMultiLock(rLocks);
			break;
		case READ:
			rLock = redissonClient.getReadWriteLock(keyList.get(0)).readLock();
			break;
		case WRITE:
			rLock = redissonClient.getReadWriteLock(keyList.get(0)).writeLock();
			break;
		// 默认可重入锁
		default:
			rLock = redissonClient.getLock(keyList.get(0));
		}
		log.error("锁模式->{}", lockModel);
		return rLock;
	}

	/**
	 * 通过spring Spel 获取参数
	 * 
	 * @param keys：定义的key值,以#开头,例如:#user
	 * @param parameterNames：形参
	 * @param values：形参值
	 * @param keyConstant:key常量
	 * @return
	 */
	public List<String> getKeyListBySpel(String[] keys, String[] parameterNames, Object[] values, String keyConstant) {
		if (keys.length == 0) {
			throw new LockException("keys不能为空");
		}
		// spel上下文
		EvaluationContext context = new StandardEvaluationContext();
		for (int j = 0; j < parameterNames.length; j++) {
			context.setVariable(parameterNames[j], values[j]);
		}
		// spel解析器
		ExpressionParser parser = new SpelExpressionParser();
		List<String> keyList = new ArrayList<>();
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			if (key.contains("#")) {
				Object value = parser.parseExpression(key).getValue(context);
				if (value != null) {
					key = value.toString();
				}
			}
			keyList.add(REDIS_LOCK_PREFIX + keyConstant + key);
		}
		log.error(" spel 表达式key={},value={}", keys, keyList);
		return keyList;
	}
}
