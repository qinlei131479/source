package com.course.common.security.config.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.course.common.security.enums.TokenStoreTypeEnum;
import com.course.common.security.propertites.PermitAllUrlProperties;
import com.course.common.security.propertites.SecurityPropertites;

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

	private final SecurityPropertites securityPropertites;
	private final PermitAllUrlProperties permitAllUrlProperties;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// JWT模式不需要远程调用
		if (securityPropertites.getTokenStoreType().equals(TokenStoreTypeEnum.jwt)) {
			resources.tokenStore(tokenStore);
		} else {
			// 使用远程服务验证令牌服务
			resources.tokenServices(tokenServices());
		}
		// 设置无状态模式
		resources.stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		permitAllUrlProperties.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
		http.csrf().disable().cors().disable().authorizeRequests().anyRequest().authenticated().and()
				// 不需要管session模式验证，只需要token验证成功即可
				.sessionManagement().disable();
	}

	/**
	 * 注入远程验证token令牌服务
	 *
	 * @return
	 */
	public ResourceServerTokenServices tokenServices() {
		switch (securityPropertites.getTokenStoreType()) {
		case jwt:
			DefaultTokenServices jwtServices = new DefaultTokenServices();
			jwtServices.setTokenStore(tokenStore);
			return jwtServices;
		default:
			RemoteTokenServices services = new RemoteTokenServices();
			services.setClientId(securityPropertites.getClientId());
			services.setClientSecret(securityPropertites.getClientSecret());
			services.setCheckTokenEndpointUrl(securityPropertites.getTokenInfoUrl());
			return services;
		}
	}
}
