package com.course.auth.controller;

import org.springframework.stereotype.Component;

/**
 * OpenFegin整合Ribbon和Hystrix，为微服务中远程调用提供了一种更优雅的调用方式，它支持负载均衡和容错熔断机制
 * 
 * @author qinlei
 * @date 2021/7/27 下午8:56
 */
@Component
public class RemoteHystrix implements RemoteClient {

	@Override
	public String helloNacos() {
		return "请求超时了";
	}
}
