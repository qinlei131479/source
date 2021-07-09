package com.course.common.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页插件注入
 * 
 * @author qinlei
 * @date 2021/6/17 上午11:19
 */
@Slf4j
@Configuration
public class MyBatisPlusConfig {

	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor
	 * = false 避免缓存出现问题
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}

	/**
	 * 自定义主键
	 * 
	 * @return
	 */
	// @Bean
	// public IdentifierGenerator idGenerator() {
	// return new IdentifierGenerator() {
	// @Override
	// public Number nextId(Object entity) {
	// // 自定义主键的生成规则,代码自实现
	// log.error("IdentifierGenerator");
	// return IdWorker.getId(entity);
	// }
	// };
	// }
}
