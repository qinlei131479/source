package com.course.common.mybatis.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qinlei
 * @date 2021/5/28 下午2:23
 */
@SpringBootApplication
@MapperScan("com.course.mybatis.plus")
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);
	}
}
