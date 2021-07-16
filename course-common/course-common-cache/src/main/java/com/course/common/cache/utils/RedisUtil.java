package com.course.common.cache.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.course.common.cache.enums.RedisKeyEnum;

/**
 * 缓存工具类Redis
 * 
 * @author qinlei
 * @date 2021/6/25 下午10:04
 */
@Component
public class RedisUtil {

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 判断key是否存在
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 普通缓存获取
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除缓存key
	 */
	public Boolean del(String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 普通缓存放入
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 指定缓存失效时间
	 *
	 * @param key：键
	 * @param time：时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * apiLog存储
	 * 
	 * @param apiLog
	 * @return
	 */
	public Long rightPushApiLogList(String apiLog) {

		if (apiLog != null && apiLog.length() > 0) {
			return redisTemplate.boundListOps(RedisKeyEnum.apilog_list.getKey()).rightPush(apiLog);
		}
		return null;
	}

	/**
	 * id列表总数
	 */
	public Long keySize() {
		return this.redisTemplate.boundListOps(RedisKeyEnum.key.getKey()).size();
	}

	/**
	 * id列表右入
	 */
	public void keyRightPush(Long key) {
		this.redisTemplate.boundListOps(RedisKeyEnum.key.getKey()).rightPush(key + "");
	}

	/**
	 * id列表左出
	 */
	public String keyLeftPop() {
		return (String) this.redisTemplate.boundListOps(RedisKeyEnum.key.getKey()).leftPop();
	}

	/**
	 * 批量处理pipeline
	 * 
	 * @param map
	 * @param seconds
	 */
	public void executePipelined(Map map, long seconds) {
		RedisSerializer serializer = redisTemplate.getStringSerializer();
		redisTemplate.executePipelined(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				if (map != null) {
					map.forEach((key, value) -> {
						connection.set(serializer.serialize(key), serializer.serialize(value),
								Expiration.seconds(seconds), RedisStringCommands.SetOption.UPSERT);
					});
				}
				return null;
			}
		}, serializer);
	}
}
