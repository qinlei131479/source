package com.course.auth.services;

import javax.sql.DataSource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import com.course.common.core.enums.RedisKeyEnum;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 重写ClientDetailsService,添加client到缓存
 * 
 * @author qinlei
 * @date 2021/8/3 下午9:33
 */
@Slf4j
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {

	public ClientDetailsServiceImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	@Cacheable(value = RedisKeyEnum.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetails details = super.loadClientByClientId(clientId);
		log.error(JSONUtil.toJsonStr(details));
		// details.getRegisteredRedirectUri();
		return details;
	}
}
