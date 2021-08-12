package com.course.auth.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/8/12 下午5:42
 */
@Slf4j
public class UserCheckTokenAuthenticationConverter extends DefaultUserAuthenticationConverter {

	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		return response;
	}
}
