package com.course.common.security.config.resources;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.course.common.security.enums.TokenStoreTypeEnum;
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
	private final ResourceServerTokenServices serverTokenServices;
	private final SecurityPropertites securityPropertites;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// JWT模式不需要远程调用
		if (securityPropertites.getTokenStoreType().equals(TokenStoreTypeEnum.jwt)) {
			resources.tokenStore(tokenStore);
		} else {
			// 使用远程服务验证令牌服务
			resources.tokenServices(serverTokenServices);
		}
		// 设置无状态模式
		resources.stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		securityPropertites.getIgnoreUrls().forEach(url -> registry.antMatchers(url).permitAll());
		http.csrf().and().cors().disable().authorizeRequests().anyRequest().authenticated().and()
				// 不需要管session模式验证，只需要token验证成功即可
				.sessionManagement().disable();
	}

}
