package com.course.common.cache.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.course.common.cache.RedisTopic;
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

	/**
	 * bitmap使用场景：日活统计、bloomfilter、点赞
	 * 
	 * @param key
	 * @param id
	 * @param flag
	 */
	public void setBitmap(String key, Long id, Boolean flag) {
		redisTemplate.opsForValue().setBit(key, id, flag);
	}

	/**
	 * 获取bitmap
	 * 
	 * @param key
	 * @param id
	 * @return
	 */
	public Boolean getBitmap(String key, Long id) {
		return redisTemplate.opsForValue().getBit(key, id);
	}

	/**
	 * 获取bitmap
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long bitCount(String key, Long start, Long end) {
		return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
	}

	public void pfAdd(String key, Long id) {
		redisTemplate.opsForHyperLogLog().add(key, id);
	}

	public Long pfCount(String key) {
		return redisTemplate.opsForHyperLogLog().size(key);
	}

	/**
	 * 合并一个新的mergeKey
	 * 
	 * @param mergeKey
	 * @param key
	 * @return
	 */
	public boolean merge(String mergeKey, String... key) {
		// pfmerge mergeKey key1 key2 ---> 将key1 key2 合并成一个新的mergeKey
		return redisTemplate.opsForHyperLogLog().union(mergeKey, key) > 0;
	}

	/**
	 * 消息发送
	 * 
	 * @param channel
	 * @param msg
	 */
	public void convertAndSend(String channel, Object msg) {
		redisTemplate.convertAndSend(channel, msg);
	}

	/**
	 * channel消息解析
	 * 
	 * @param message
	 * @return
	 */
	public Object parseMessage(Message message) {
		byte[] body = message.getBody();
		byte[] channel = message.getChannel();
		String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);
		if (!topic.equals(RedisTopic.topic)) {
			return null;
		}
		return redisTemplate.getValueSerializer().deserialize(body);
	}
}
