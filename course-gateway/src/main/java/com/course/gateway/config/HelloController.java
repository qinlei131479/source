package com.course.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinlei
 * @date 2021/7/27 下午4:01
 */
@RefreshScope
@RestController
public class HelloController {

	@Value("${test:'ss'}")
	private String name;
	@Value("${server.port:8888}")
	private String port;
	@Value("${server.title:'name'}")
	private String title;

	@GetMapping("/hello")
	public String helloNacos() {
		return "你好，nacos！" + "," + name + "," + port+ "," + title;
	}
}
