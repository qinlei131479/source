package com.course.auth.config;

import com.course.auth.propertites.SecurityPropertites;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * TokenStore 相关配置
 * 
 * @author qinlei
 * @date 2021/7/31 下午10:32
 */
@Configuration
@RequiredArgsConstructor
public class TokenStoreConfig {

	private final SecurityPropertites securityPropertites;
	/**
	 * token存储位置
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		// 内存模式
		// return new InMemoryTokenStore();
		// JWT模式
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * JwtAccessToken注入
	 * 
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(securityPropertites.getJwtSigningKey());
		return converter;
	}
}
