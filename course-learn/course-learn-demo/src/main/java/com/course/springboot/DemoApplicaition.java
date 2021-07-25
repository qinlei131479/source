package com.course.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.course.common.job.annotation.CourseEnableXxlJob;

/**
 * 当@service无法注册时，添加@ComponentScan("com.course")
 * 当mapper文件无法注册时，添加 @MapperScan(basePackages = { "com.course.*.*.mapper",
 * "com.course.*.mapper" })
 * 
 * @author qinlei
 * @date 2021/6/10 下午2:48
 */
// @CourseEnableXxlJob
@SpringBootApplication
public class DemoApplicaition {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplicaition.class, args);
	}
}
