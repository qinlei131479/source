package com.course.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成应用
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@SpringBootApplication
public class GeneratorCodeApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GeneratorCodeApplication.class);
		app.run(args);
	}
}
