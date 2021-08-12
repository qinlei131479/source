package com.course.common.core.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

/**
 * redis配置注入
 * 
 * @author qinlei
 * @date 2021/6/25 下午10:02
 */
@EnableCaching
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfig {

	private final RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(keySerializer());
		redisTemplate.setHashKeySerializer(keySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}

	/**
	 * spring cache 注解相关序列化操作
	 */
	@Bean
	public CacheManager cacheManager() {
		// 配置序列化
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		RedisCacheConfiguration redisCacheConfiguration = config
				// 变双冒号为单冒号
				.computePrefixWith(name -> name + ":")
				// 键序列化方式 redis字符串序列化
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				// 值序列化方式 简单json序列化
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
		return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
	}

	/**
	 * key 类型序列化
	 *
	 * @return
	 */
	@Bean
	public RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * 值采用JSON序列化 解决LocalDateTime无法redis序列化问题
	 *
	 * @return
	 */
	@Bean
	public RedisSerializer<Object> valueSerializer() {
		// ObjectMapper om = new ObjectMapper();
		// om.setVisibility(PropertyAccessor.ALL,
		// JsonAutoDetect.Visibility.ANY);
		// om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
		// ObjectMapper.DefaultTyping.NON_FINAL,
		// JsonTypeInfo.As.PROPERTY);
		// // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		//
		// // LocalDatetime序列化
		// DateTimeFormatter date =
		// DateTimeFormatter.ofPattern(CommonConstants.DATE_FORMATTER);
		// DateTimeFormatter dateTime =
		// DateTimeFormatter.ofPattern(CommonConstants.DATETIME_FORMATTER);
		//
		// JavaTimeModule timeModule = new JavaTimeModule();
		// timeModule.addDeserializer(LocalDate.class, new
		// LocalDateDeserializer(date));
		// timeModule.addDeserializer(LocalDateTime.class, new
		// LocalDateTimeDeserializer(dateTime));
		// timeModule.addSerializer(LocalDate.class, new
		// LocalDateSerializer(date));
		// timeModule.addSerializer(LocalDateTime.class, new
		// LocalDateTimeSerializer(dateTime));
		//
		// om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// om.registerModule(timeModule);
		return new GenericJackson2JsonRedisSerializer();
	}

	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

	/**
	 * 基于bitmap实现；用途：记录网站ip注册数、每日访问ip数、页面实时UV、在线用户人数
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HyperLogLogOperations<String, Object> hyperLogLogOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHyperLogLog();
	}

	/**
	 * 地理位置
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public GeoOperations<String, Object> geoOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForGeo();
	}

	@Bean
	@ConditionalOnBean(MessageListenerAdapter.class)
	RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		// 配置名称
		container.addMessageListener(listenerAdapter, new PatternTopic(RedisTopic.topic));
		return container;
	}
}
