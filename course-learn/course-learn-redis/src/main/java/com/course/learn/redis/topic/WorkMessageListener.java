package com.course.learn.redis.topic;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.course.common.cache.utils.RedisUtil;
import com.course.learn.redis.entity.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * channel 监听
 * 
 * @author qinlei
 * @date 2021/7/16 下午10:30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WorkMessageListener extends MessageListenerAdapter {

	private final RedisUtil redisUtil;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		Object res = redisUtil.parseMessage(message);
		if (res != null) {
			if (res instanceof Notice) {
				Notice notice = (Notice) res;
				log.error("name = {},value = {}", notice.getName(), notice.getVal());
			}
		}
	}
}