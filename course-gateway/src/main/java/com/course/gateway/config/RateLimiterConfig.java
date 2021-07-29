package com.course.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

/**
 * 限流方式配置
 * 
 * @author qinlei
 * @date 2021/7/29 下午4:06
 */
@Configuration
public class RateLimiterConfig {

	@Bean(value = "remoteAddrKeyResolver")
	public KeyResolver remoteAddrKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
	}

}
