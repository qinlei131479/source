package com.course.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 资源服务器配置
 * 
 * @author qinlei
 * @date 2021/7/31 下午6:41
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 使用远程服务验证令牌服务
		// 设置无状态模式
		resources.tokenServices(tokenServices()).stateless(true);
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
	@Bean
	public ResourceServerTokenServices tokenServices() {
		RemoteTokenServices services = new RemoteTokenServices();
		services.setClientId("test");
		services.setClientSecret("test");
		services.setCheckTokenEndpointUrl("http://localhost:8898/oauth/check_token");
		return services;
	}
}