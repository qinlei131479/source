package com.course.springboot;

import com.course.common.job.annotation.CourseEnableXxlJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qinlei
 * @date 2021/6/10 下午2:48
 */
@CourseEnableXxlJob
@SpringBootApplication
public class CourseApplicaition {

	public static void main(String[] args) {
		SpringApplication.run(CourseApplicaition.class, args);
	}
}
