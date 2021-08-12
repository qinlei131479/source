package com.course.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.course.common.core.entity.Res;
import com.course.common.security.entity.CourseUser;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 自定义用户登录和授权页面
 * 
 * @author qinlei
 * @date 2021/8/5 下午9:40
 */
@Controller
@RequiredArgsConstructor
public class TokenController {

	private final ClientDetailsService clientDetailsService;
	private final TokenStore tokenStore;

	/**
	 * 认证页面
	 *
	 * @param modelAndView
	 * @param error
	 *            表单登录失败处理回调的错误信息
	 * @return ModelAndView
	 */
	@GetMapping("/token/login")
	public ModelAndView login(ModelAndView modelAndView, @RequestParam(required = false) String error) {
		modelAndView.setViewName("login");
		modelAndView.addObject("error", error);
		return modelAndView;
	}

	/**
	 * 认证页面
	 *
	 * @param modelAndView
	 * @param error
	 *            表单登录失败处理回调的错误信息
	 * @return ModelAndView
	 */
	@GetMapping(value = { "/token/index", "/" })
	public ModelAndView index(ModelAndView modelAndView, @RequestParam(required = false) String error) {
		modelAndView.setViewName("index");
		CourseUser user = (CourseUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	/**
	 * 确认授权页面
	 *
	 * @param request
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@GetMapping("/token/confirm_access")
	public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
		// 只能从request获取，若从ClientDetails获取，造成错误：error=access_denied&error_description=User%20denied%20access
		Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
		modelAndView.addObject("scopeList", scopeList.keySet());

		Object auth = session.getAttribute("authorizationRequest");
		if (auth != null) {
			AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
			modelAndView.addObject("app", clientDetails);
			modelAndView.addObject("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/**
	 * 退出并删除token
	 *
	 * @param authHeader
	 *            Authorization
	 * @return
	 */
	@DeleteMapping("/token/logout")
	public Res<Object> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StrUtil.isBlank(authHeader)) {
			return Res.succ();
		}
		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
		return removeToken(tokenValue);
	}

	/**
	 * 令牌管理调用
	 *
	 * @param token
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	public Res<Object> removeToken(@PathVariable("token") String token) {
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		if (accessToken != null && StrUtil.isNotBlank(accessToken.getValue())) {
			// 清空access token
			tokenStore.removeAccessToken(accessToken);
			// 清空 refresh token
			tokenStore.removeRefreshToken(accessToken.getRefreshToken());
		}
		return Res.succ();
	}
}
