package com.course.auth.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.course.auth.services.ClientDetailsServiceImpl;
import com.course.common.core.constant.SecurityConstants;
import com.course.common.security.entity.CourseUser;
import com.course.common.security.enums.TokenStoreTypeEnum;
import com.course.common.security.propertites.SecurityPropertites;

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

	private final SecurityPropertites securityPropertites;
	/**
	 * 用户认证
	 */
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final CourseWebResponseExceptionTranslator courseWebResponseExceptionTranslator;
	/**
	 * token存储位置和token增强
	 */
	private final TokenStore tokenStore;
	private final JwtAccessTokenConverter accessTokenConverter;
	/**
	 * 客户端数据源注入
	 */
	private final ClientDetailsService clientDetailsService;
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 1、配置客户端:可通过数据库加载
	 *
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 1、jdbc数据源模式注入客户端
		ClientDetailsServiceImpl clientDetailsService = new ClientDetailsServiceImpl(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		// 注入加密方式
		clientDetailsService.setPasswordEncoder(passwordEncoder);
		clients.withClientDetails(clientDetailsService);
		// 2、内存模式配置:
		// clients.inMemory().withClient(securityPropertites.getClientId())
		// client秘钥、授权范围、跳转授权页面
		// .secret(passwordEncoder.encode("test")).scopes("all").autoApprove(false)
		// .redirectUris("https://www.baidu.com")
		// 允许的授权类型
		// .authorizedGrantTypes("authorization_code", "password",
		// "client_credentials", "implicit",
		// "refresh_token");
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
				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
				// 认证管理器
				.authenticationManager(authenticationManager)
				// 令牌存储方式
				.tokenServices(tokenServices())
				// 密码模式用户信息管理
				.userDetailsService(userDetailsService)
				// 授权码
				.authorizationCodeServices(authorizationCodeServices())
				// 允许访问模式
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				// 自定义异常栈解析
				.exceptionTranslator(courseWebResponseExceptionTranslator);
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
		services.setTokenStore(tokenStore);
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		// 采用JWT模式需要配置增强器
		if (TokenStoreTypeEnum.jwt.equals(securityPropertites.getTokenStoreType())) {
			delegates.add(accessTokenConverter);
		}
		delegates.add(tokenEnhancer());
		enhancerChain.setTokenEnhancers(delegates);
		services.setTokenEnhancer(enhancerChain);
		// 允许令牌token自动刷新
		services.setSupportRefreshToken(true);
		return services;
	}

	/**
	 * 授权码储存方式注入
	 * 
	 * @return
	 */
	@Bean
	public AuthorizationCodeServices authorizationCodeServices() {
		// 1、内存模式
		// return new InMemoryAuthorizationCodeServices();
		// 2、jdbc模式
		JdbcAuthorizationCodeServices codeServices = new JdbcAuthorizationCodeServices(dataSource);
		codeServices.setInsertAuthenticationSql(SecurityConstants.CODE_STATEMENT_INSERT);
		codeServices.setSelectAuthenticationSql(SecurityConstants.CODE_STATEMENT_SELECT);
		codeServices.setDeleteAuthenticationSql(SecurityConstants.CODE_STATEMENT_DELETE);
		// 3、redis模式
		return codeServices;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(4);
			CourseUser courseUser = (CourseUser) authentication.getUserAuthentication().getPrincipal();
			additionalInfo.put(SecurityConstants.DETAILS_LICENSE, "made in course");
			additionalInfo.put(SecurityConstants.DETAILS_USER_ID, courseUser.getUserId());
			additionalInfo.put(SecurityConstants.DETAILS_USERNAME, courseUser.getUsername());
			additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, courseUser.getDeptId());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}
