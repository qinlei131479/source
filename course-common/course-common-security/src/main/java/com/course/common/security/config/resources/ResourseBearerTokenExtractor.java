package com.course.common.security.config.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.course.common.security.propertites.PermitAllUrlProperties;

import lombok.RequiredArgsConstructor;

/**
 * 对公开权限的请求不进行校验
 * 
 * @author qinlei
 * @date 2021/8/7 下午6:50
 */
@Component
@RequiredArgsConstructor
public class ResourseBearerTokenExtractor extends BearerTokenExtractor {

	private final PathMatcher pathMatcher = new AntPathMatcher();
	private final PermitAllUrlProperties urlProperties;

	@Override
	public Authentication extract(HttpServletRequest request) {
		boolean match = urlProperties.getUrls().stream()
				.anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
		return match ? null : super.extract(request);
	}
}
