package com.course.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.course.auth.handler.LoginFailHandler;
import com.course.auth.handler.LoginSuccessHandler;
import com.course.common.security.constant.SecurityConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * spring Security安全框架配置(认证、资源服务端配置)
 *
 * @author qinlei
 * @date 2021/7/31 下午2:13
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final LoginSuccessHandler loginSuccessHandler;
	private final LoginFailHandler loginFailHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 链式配置拦截策略
		http.formLogin().loginPage(SecurityConstant.LOGIN_PAGE).loginProcessingUrl(SecurityConstant.LOGIN_PROCESS_URL)
				// 配置登录成功或失败处理器
				.successHandler(loginSuccessHandler).failureHandler(loginFailHandler)
				// 配置登出url
				.and().logout().logoutUrl(SecurityConstant.LOGOUT_URL).logoutSuccessUrl("/")
				// 配置不需要验证的url
				.and().authorizeRequests().antMatchers(SecurityConstant.permitAllUrls).permitAll().anyRequest()
				.authenticated()
				// 禁用csrf
				.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/css/**");
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
