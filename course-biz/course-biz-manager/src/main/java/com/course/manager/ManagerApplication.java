package com.course.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.course.common.security.annotation.CourseEnableResourceServer;

/**
 * 资源服务 - 后台管理系统
 * 
 * @author qinlei
 * @date 2021/7/31 下午2:11
 */
@SpringBootApplication
@CourseEnableResourceServer
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
}
