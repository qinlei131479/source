package com.course.auth.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String test() {
		return remoteClient.helloNacos();
	}
}
