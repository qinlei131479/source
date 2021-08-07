package com.course.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.course.common.cache.enums.RedisKeyEnum;
import com.course.common.security.propertites.SecurityPropertites;

import lombok.RequiredArgsConstructor;

/**
 * spring Security安全框架配置(认证、资源服务端配置)
 *
 * @author qinlei
 * @date 2021/7/31 下午2:13
 */
@Configuration
@RequiredArgsConstructor
public class TokenStoreConfig {

	private final SecurityPropertites securityPropertites;
	private final RedisConnectionFactory redisConnectionFactory;

	/**
	 * token存储位置<br>
	 * 1、InMemoryTokenStore 内存模式<br>
	 * 2、JwtTokenStore JWT模式（默认）<br>
	 * 3、RedisTokenStore redis模式（推荐）<br>
	 * 4、JdbcTokenStore Jdbc模式<br>
	 * 
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		switch (securityPropertites.getTokenStoreType()) {
		case redis:
			RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
			redisTokenStore.setPrefix(RedisKeyEnum.OAUTH_PREFIX);
			return redisTokenStore;
		case jwt:
			return new JwtTokenStore(accessTokenConverter());
		default:
			return new InMemoryTokenStore();
		}
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
