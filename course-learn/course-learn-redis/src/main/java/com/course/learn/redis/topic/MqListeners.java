package com.course.learn.redis.topic;

import org.springframework.stereotype.Component;

import com.course.common.cache.RedisTopic;
import com.course.common.cache.annotation.MqListener;
import com.course.common.cache.enums.MqModel;
import com.course.learn.redis.entity.Notice;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Redisson监听发布订阅消息
 * 
 * @author qinlei
 * @date 2021/7/22 下午10:07
 */
@Slf4j
@Component
public class MqListeners {

	/**
	 * 接受消息方式一:PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic
	 * (如果消息类型不明确,请使用Object 接收消息)
	 * 
	 * @param charSequence
	 * @param notice
	 */
	@MqListener(name = RedisTopic.redisson_topic)
	public void customer(CharSequence charSequence, Notice notice, Object object) {
		log.error("charSequence = {}", charSequence);
		log.error("收到消息Notice = {},收到消息Object = {}", JSONUtil.toJsonStr(notice), object);
	}

	/**
	 *
	 * 接收消息方式二: PATTERN模糊匹配 如: name="myTopic.*" 那么发送者的topic name 可以是
	 * myTopic.name1 myTopic.name2.尾缀不限定
	 * 
	 * @param patten
	 * @param charSequence
	 * @param notice
	 */
	@MqListener(name = RedisTopic.redisson_pattern + "*", model = MqModel.PATTERN)
	public void customer(CharSequence patten, CharSequence charSequence, Notice notice) {
		log.error("charSequence = {}", charSequence);
		log.error("收到消息Notice = {},收到消息patten = {}", JSONUtil.toJsonStr(notice), patten);
	}
}
