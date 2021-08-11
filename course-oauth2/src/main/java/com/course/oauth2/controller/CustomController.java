package com.course.oauth2.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import com.course.common.core.entity.Res;

import lombok.RequiredArgsConstructor;

/**
 * @author qinlei
 * @date 2021/8/9 下午2:55
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class CustomController {

	private final TokenEndpoint tokenEndpoint;

	@GetMapping("/token")
	public Object getAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
			throws HttpRequestMethodNotSupportedException {
		return this.result(principal, parameters);
	}

	@PostMapping("/token")
	public Object postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
			throws HttpRequestMethodNotSupportedException {
		return this.result(principal, parameters);
	}

	/**
	 * 包装返回数据
	 *
	 * @param principal
	 * @param parameters
	 * @return
	 * @throws HttpRequestMethodNotSupportedException
	 */
	public Object result(Principal principal, @RequestParam Map<String, String> parameters)
			throws HttpRequestMethodNotSupportedException {
		ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.getAccessToken(principal, parameters);
		OAuth2AccessToken body = accessToken.getBody();
		Map<String, Object> customMap = body.getAdditionalInformation();
		String value = body.getValue();
		OAuth2RefreshToken refreshToken = body.getRefreshToken();
		Set<String> scope = body.getScope();
		int expiresIn = body.getExpiresIn();
		customMap.put("token", value);
		customMap.put("scope", scope);
		customMap.put("expiresIn", expiresIn);
		customMap.put("refreshToken", refreshToken);
		return Res.succ(customMap);
	}
}
