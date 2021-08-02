package com.course.common.security.config.resources;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/8/2 下午1:23
 */
@Slf4j
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private final TokenStore tokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 使用远程服务验证令牌服务
		// 设置无状态模式
		// resources.tokenServices(tokenServices()).stateless(true);
		resources.tokenStore(tokenStore).stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().permitAll().and()
				// 不需要管session模式验证，只需要token验证成功即可
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * 注入远程验证token令牌服务
	 *
	 * @return
	 */
	// @Bean
	// public ResourceServerTokenServices tokenServices() {
	// RemoteTokenServices services = new RemoteTokenServices();
	// services.setClientId("test");
	// services.setClientSecret("test");
	// services.setCheckTokenEndpointUrl("http://localhost:8898/oauth/check_token");
	// return services;
	// }
}
