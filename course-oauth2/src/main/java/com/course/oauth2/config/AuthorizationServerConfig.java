package com.course.oauth2.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import lombok.RequiredArgsConstructor;

/**
 * @author qinlei
 * @date 2021/8/9 下午2:28
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailService;
	private final CourseWebResponseExceptionTranslator webResponseExceptionTranslator;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// client_id
		clients.inMemory().withClient("test")
				// client_secret
				.secret(passwordEncoder.encode("test"))
				// 该client允许的授权类型
				.authorizedGrantTypes("authorization_code", "client_credentials", "password", "refresh_token",
						"implicit")
				// 允许的授权范围
				.scopes("server")
				// 资源服务器id,需要与资源服务器对应
				.redirectUris("https://www.baidu.com")
				// 设置token有效期
				.accessTokenValiditySeconds(7 * 24 * 3600)
				// 设置refreshToken有效期
				.refreshTokenValiditySeconds(7 * 24 * 3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		// // 这里可以替换code模式等uri地址
		//endpoints.pathMapping("/oauth/confirm_access", "/custom/confirm_access");
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		endpoints.exceptionTranslator(webResponseExceptionTranslator);
		// authenticationManager配合password模式使用
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailService)
				// 这里使用内存存储token,也可以使用redis和数据库
				.tokenStore(new InMemoryTokenStore());
		endpoints.tokenEnhancer(new TokenEnhancer() {
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
					OAuth2Authentication oAuth2Authentication) {
				// 在返回token的时候可以加上一些自定义数据
				DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("nickname", "测试姓名");
				token.setAdditionalInformation(map);
				return token;
			}
		});
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 配置允许认证的权限
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.authenticationEntryPoint(new AuthExceptionEntryPoint());
		// 开启支持通过表单方式提交client_id和client_secret,否则请求时以basic
		// auth方式,头信息传递Authorization发送请求
		security.allowFormAuthenticationForClients();
	}
}
