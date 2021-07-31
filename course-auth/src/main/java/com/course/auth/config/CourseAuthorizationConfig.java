package com.course.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import lombok.RequiredArgsConstructor;

/**
 * 授权认证配置 <br>
 * 1、配置客户端:可通过数据库加载 <br>
 * 2、配置令牌token和令牌服务tokenServices <br>
 * 3、配置令牌端点的安全约束
 * 
 * @author qinlei
 * @date 2021/7/31 下午2:18
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class CourseAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ClientDetailsService clientDetailsService;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;

	/**
	 * 1、配置客户端:可通过数据库加载
	 *
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 内存模式配置
		clients.inMemory().withClient("test")
				// client秘钥
				.secret(passwordEncoder.encode("test"))
				// 允许的授权类型
				.authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit",
						"refresh_token")
				// 允许的授权范围
				.scopes("all")
				// 跳转授权页面
				.autoApprove(false)
				// 回调地址
				.redirectUris("https://www.baidu.com");
	}

	/**
	 * 2、配置令牌token和令牌服务tokenServices
	 *
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				// 定制授权页面
				.pathMapping("/oauth/confirm_access", "/customer/confirm_access")
				// 认证管理器
				.authenticationManager(authenticationManager)
				// 令牌存储方式
				.tokenServices(tokenServices())
				// 密码模式用户信息管理
				.userDetailsService(userDetailsService)
				// 授权码
				.authorizationCodeServices(authorizationCodeServices())
				// 允许访问模式
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}

	/**
	 * 3、配置令牌端点的安全约束
	 *
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				// oauth/token_key公开
				.tokenKeyAccess("permitAll()")
				// oauth/check_token公开
				.checkTokenAccess("permitAll()")
				// 表单认证申请令牌
				.allowFormAuthenticationForClients();
	}

	/**
	 * 令牌服务配置：默认实现
	 * {@link org.springframework.security.oauth2.provider.token.DefaultTokenServices}
	 *
	 * @return
	 */
	public AuthorizationServerTokenServices tokenServices() {
		DefaultTokenServices services = new DefaultTokenServices();
		// client信息管理
		services.setClientDetailsService(clientDetailsService);
		// 令牌存储方式
		services.setTokenStore(tokenStore());
		// 允许令牌token自动刷新
		services.setSupportRefreshToken(true);
		// 令牌有效期，2小时
		services.setAccessTokenValiditySeconds(7200);
		// 刷新令牌有效期
		services.setRefreshTokenValiditySeconds(259200);
		return services;
	}

	/**
	 * token存储位置
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	/**
	 * 授权码储存方式注入
	 * 
	 * @return
	 */
	@Bean
	public AuthorizationCodeServices authorizationCodeServices() {
		return new InMemoryAuthorizationCodeServices();
	}
}
