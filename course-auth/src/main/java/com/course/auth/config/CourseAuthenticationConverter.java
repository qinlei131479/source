package com.course.auth.config;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * 重写check_token返回数据的方法convertAccessToken
 * 
 * @author qinlei
 * @date 2021/8/12 下午5:42
 */
@Slf4j
public class CourseAuthenticationConverter extends DefaultAccessTokenConverter {

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		// super.convertAccessToken(token, authentication)
		Map<String, Object> customMap = token.getAdditionalInformation();
		customMap.put("accessToken", token.getValue());
		customMap.put("expiresIn", token.getExpiresIn());
		customMap.put("refreshToken", token.getRefreshToken().getValue());
		return customMap;
	}
}
