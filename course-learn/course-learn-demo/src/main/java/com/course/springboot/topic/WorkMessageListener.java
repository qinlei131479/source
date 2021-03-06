package com.course.springboot.topic;

import com.course.common.core.entity.Res;
import com.course.common.core.redis.RedisUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.course.springboot.entity.User;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * channel ηε¬
 * 
 * @author qinlei
 * @date 2021/7/16 δΈε10:30
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
			if (res instanceof Res) {
			}
			log.error("name = {},value = {}", res, JSONUtil.toJsonStr(res));
		}
	}
}
