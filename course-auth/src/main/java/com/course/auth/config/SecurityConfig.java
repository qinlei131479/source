package com.course.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.course.auth.handler.LoginFailHandler;
import com.course.auth.handler.LoginSuccessHandler;
import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;

/**
 * spring Security安全框架配置(认证、资源服务端配置)
 *
 * @author qinlei
 * @date 2021/7/31 下午2:13
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final LoginSuccessHandler loginSuccessHandler;
	private final LoginFailHandler loginFailHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 链式配置拦截策略
		http.csrf().and().cors().disable().authorizeRequests().anyRequest().authenticated();
		http.formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailHandler);
		http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
			WebUtil.responseWriteJson(response, JSONUtil.toJsonStr(Res.noPower()));
		});
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * 密码解析器
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
