package com.course.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//import com.course.common.job.annotation.CourseEnableXxlJob;

/**
 * @author qinlei
 * @date 2021/6/10 下午2:48
 */
// @CourseEnableXxlJob
@ComponentScan("com.course")
@MapperScan(basePackages = { "com.course.*.*.mapper", "com.course.*.mapper" })
@SpringBootApplication
public class CourseApplicaition {

	public static void main(String[] args) {
		SpringApplication.run(CourseApplicaition.class, args);
	}
}
