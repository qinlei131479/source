package com.course.gateway.handler;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.course.common.cache.enums.RedisKeyEnum;
import com.course.common.cache.utils.RedisUtil;
import com.course.gateway.properties.GatewayConfigProperties;
import com.pig4cloud.captcha.ArithmeticCaptcha;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 生成验证码
 * 
 * @author qinlei
 * @date 2021/7/29 下午4:04
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {

	private final RedisUtil redisUtil;
	private final GatewayConfigProperties configProperties;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {

		ArithmeticCaptcha captcha = new ArithmeticCaptcha(configProperties.getImageWidth(),
				configProperties.getImageHeigth());
		String result = captcha.text();

		// 保存验证码信息
		String randomStr = serverRequest.queryParam("randomStr").get();
		// redisTemplate.setKeySerializer(new StringRedisSerializer());
		String key = RedisKeyEnum.validCode.getKey(randomStr);
		redisUtil.set(key, result, configProperties.getTimeout());
		// redisTemplate.opsForValue().set(key, result,
		// SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		captcha.out(os);

		return ServerResponse.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG)
				.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}
}
