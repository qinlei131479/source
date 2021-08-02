package com.course.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.course.gateway.handler.ImageCodeHandler;

import lombok.RequiredArgsConstructor;

/**
 * 验证码路由配置
 * 
 * @author qinlei
 * @date 2021/7/29 下午4:06
 */
@Configuration
@RequiredArgsConstructor
public class RouterConfig {

	private final ImageCodeHandler imageCodeHandler;

	@Bean
	public RouterFunction routerFunction() {
		return RouterFunctions.route(
				RequestPredicates.path("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeHandler);
	}

}
