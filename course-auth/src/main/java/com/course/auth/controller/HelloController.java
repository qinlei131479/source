package com.course.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.core.entity.Res;

import lombok.RequiredArgsConstructor;

/**
 * @author qinlei
 * @date 2021/7/27 下午8:48
 */
@RestController
@RequiredArgsConstructor
public class HelloController {

	private final RemoteClient remoteClient;

	@GetMapping("/feign")
	public String feign() {
		return remoteClient.helloNacos();
	}

	@GetMapping("/test")
	public Res test(String name) {
		return Res.succ("hello name," + name);
	}

	@GetMapping("/")
	public String test2() {
		return "hello name 333";
	}
}
