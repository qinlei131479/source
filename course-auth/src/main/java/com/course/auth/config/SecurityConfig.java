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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String REDIRECT_URI = "redirect_uri";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 链式配置拦截策略
		http.formLogin().loginPage("/user/login").loginProcessingUrl("/user/doLogin").successHandler(successHandler())
				.failureHandler(failureHandler()).and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/").and()
				.authorizeRequests().antMatchers("/user/login", "/user/doLogin", "/user/logout", "/actuator/**")
				.permitAll().anyRequest().authenticated().and().csrf().disable();
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

	/**
	 * 表单登录成功
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return (request, response, authentication) -> {
			if (WebUtil.isApplicationJson(request)) {
				WebUtil.responseWriteJson(response, Res.succ());
			} else {
				log.debug("表单登录成功:{}", authentication);
				// 获取请求参数中是否包含 回调地址
				String redirectUrl = request.getParameter(REDIRECT_URI);
				redirectUrl = StrUtil.isNotBlank(redirectUrl) ? redirectUrl : "/user/index";
				response.sendRedirect(redirectUrl);
			}
		};
	}

	/**
	 * 表单登录失败
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return (request, response, exception) -> {
			if (WebUtil.isApplicationJson(request)) {
				WebUtil.responseWriteJson(response, Res.fail(exception.getMessage()));
			} else {
				log.debug("表单登录失败:{}", exception.getLocalizedMessage());
				String uri = HttpUtil.encodeParams(String.format("/user/login?error=%s", exception.getMessage()),
						CharsetUtil.CHARSET_UTF_8);
				response.sendRedirect(uri);
			}
		};
	}
}
