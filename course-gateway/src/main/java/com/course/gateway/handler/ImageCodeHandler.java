package com.course.gateway.handler;

import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.course.common.cache.enums.RedisKeyEnum;
import com.course.common.cache.utils.RedisUtil;
import com.course.common.core.entity.Res;
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
		Optional<String> randomStr = serverRequest.queryParam("randomStr");
		if (!randomStr.isPresent()) {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.bodyValue(Res.fail("randomStr is not null"));
		}

		String key = RedisKeyEnum.validCode.getKey(randomStr.get());
		redisUtil.set(key, result, configProperties.getTimeout());

		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		captcha.out(os);
		return ServerResponse.ok().contentType(MediaType.IMAGE_JPEG)
				.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}
}
