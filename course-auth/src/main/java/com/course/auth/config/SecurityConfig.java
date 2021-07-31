package com.course.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.RequiredArgsConstructor;

/**
 * spring Security安全框架配置(认证服务端配置)
 *
 * @author qinlei
 * @date 2021/7/31 下午2:17
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// private final TokenStore tokenStore;
	// private final ClientDetailsService clientDetailsService;

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

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(
				User.withUsername("admin").password(passwordEncoder().encode("123456")).authorities("admin").build(),
				User.withUsername("user").password(passwordEncoder().encode("123456")).authorities("user").build());
		return userDetailsManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 链式配置拦截策略
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().formLogin();
	}
}
