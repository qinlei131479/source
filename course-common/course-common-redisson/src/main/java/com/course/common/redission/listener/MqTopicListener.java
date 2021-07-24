package com.course.common.redission.listener;

import java.lang.reflect.Method;
import java.util.Map;

import org.redisson.api.RPatternTopic;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.course.common.redission.annotation.MqListener;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/7/24 下午1:56
 */
@Slf4j
@Component
public class MqTopicListener implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		RedissonClient redissonClient = applicationContext.getBean(RedissonClient.class);
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(MqListener.class);
		for (Object bean : map.values()) {
			ReflectionUtils.doWithMethods(bean.getClass(), method -> {
				MqListener annotation = AnnotationUtils.findAnnotation(method, MqListener.class);
				if (annotation != null) {
					switch (annotation.model()) {
					// 精准匹配
					case PRECISE:
						RTopic topic = redissonClient.getTopic(annotation.name());
						topic.addListener(Object.class, (channel, msg) -> {
							messageListener(bean, method, channel, msg, null);
						});
						log.info("注解redisson精准监听器 name = {}", annotation.name());
						break;
					// 模糊匹配
					default:
						RPatternTopic patternTopic = redissonClient.getPatternTopic(annotation.name());
						patternTopic.addListener(Object.class, (pattern, channel, msg) -> {
							messageListener(bean, method, channel, msg, pattern);
						});
						log.info("注解redisson模糊监听器 name = {}", annotation.name());
						break;
					}
				}
			}, ReflectionUtils.USER_DECLARED_METHODS);
		}
	}

	/**
	 * 消息监听，获取发布订阅消息执行器
	 *
	 * @param bean
	 * @param method
	 * @param channel
	 * @param msg
	 * @param pattern
	 */
	private void messageListener(Object bean, Method method, CharSequence channel, Object msg, CharSequence pattern) {
		try {
			Object[] params = new Object[method.getParameterTypes().length];
			int index = 0;
			boolean patternFlag = false;
			for (Class parameterType : method.getParameterTypes()) {
				String simpleName = parameterType.getSimpleName();
				if ("CharSequence".equals(simpleName)) {
					if (StrUtil.isNotBlank(pattern) && !patternFlag) {
						patternFlag = true;
						params[index++] = pattern;
					} else {
						params[index++] = channel;
					}
				} else if (msg.getClass().getSimpleName().equals(simpleName) || "Object".equals(simpleName)) {
					params[index++] = msg;
				} else {
					params[index++] = null;
				}
			}
			method.invoke(bean, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
