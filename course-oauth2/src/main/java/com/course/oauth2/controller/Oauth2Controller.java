package com.course.oauth2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.course.common.core.entity.Res;

/**
 * @SessionAttributes("authorizationRequest") // 必须配置该作用域设置
 *
 * @author qinlei
 * @date 2021/8/9 下午2:47
 */
@Controller
@SessionAttributes("authorizationRequest")
public class Oauth2Controller {

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@RequestMapping("/authentication/require")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Res requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (null != savedRequest) {
			String targetUrl = savedRequest.getRedirectUrl();
			System.out.println("引发跳转的请求是:" + targetUrl);
			redirectStrategy.sendRedirect(request, response, "/ologin");
		}
		// 如果访问的是接口资源
		Res res = Res.noLogin();
		res.setMsg("访问的服务需要身份认证，请引导用户到登录页");
		return res;
	}

	@RequestMapping("/ologin")
	public String oauthLogin() {
		return "oauthLogin";
	}

	/**
	 * 授权控制器
	 *
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/oauth/confirm_access")
	public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes")
				: request.getAttribute("scopes"));
		List<String> scopeList = new ArrayList<>();
		if (scopes != null) {
			scopeList.addAll(scopes.keySet());
		}
		model.put("scopeList", scopeList);
		return "oauthGrant";
	}
}
