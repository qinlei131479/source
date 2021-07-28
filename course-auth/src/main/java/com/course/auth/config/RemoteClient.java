package com.course.auth.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qinlei
 * @date 2021/7/27 下午8:54
 */
@FeignClient(name = "course-gateway", fallback = RemoteHystrix.class)
public interface RemoteClient {

	/**
	 * 内部调用接口
	 * 
	 * @return
	 */
	@GetMapping("/hello")
	String helloNacos();
}
