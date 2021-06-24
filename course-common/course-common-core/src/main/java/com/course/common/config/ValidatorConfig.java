package com.course.common.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证方式2种模式，开启快速验证模式
 * 
 * @author qinlei
 * @date 2021/6/24 下午4:02
 */
@Configuration
public class ValidatorConfig {

	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
				.addProperty("hibernate.validator.fail_fast", "true").failFast(true).buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
