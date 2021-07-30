package com.course.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.course.gateway.constant.CommonConstant;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author qinlei
 * @date 2021/7/29 下午4:05
 */
@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove(CommonConstant.FORM)).build();

		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L)
				.collect(Collectors.joining("/"));
		ServerHttpRequest newRequest = request.mutate().path(newPath).build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	@Override
	public int getOrder() {
		return -1000;
	}
}
