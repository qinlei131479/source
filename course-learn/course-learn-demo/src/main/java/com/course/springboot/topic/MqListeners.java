package com.course.springboot.topic;

import org.springframework.stereotype.Component;

import com.course.common.redission.CommonTopic;
import com.course.common.redission.annotation.MqListener;
import com.course.common.redission.enums.MqModel;
import com.course.springboot.entity.User;

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
@MqListener
public class MqListeners {

	/**
	 * 接受消息方式一:PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic
	 * (如果消息类型不明确,请使用Object 接收消息)
	 * 
	 * @param charSequence
	 * @param notice
	 */
	@MqListener(name = CommonTopic.redisson_topic)
	public void customer(CharSequence charSequence, User notice, Object object) {
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
	@MqListener(name = CommonTopic.redisson_pattern + "*", model = MqModel.PATTERN)
	public void customer(CharSequence patten, CharSequence charSequence, User notice) {
		log.error("charSequence = {}", charSequence);
		log.error("收到消息Notice = {},收到消息patten = {}", JSONUtil.toJsonStr(notice), patten);
	}
}
