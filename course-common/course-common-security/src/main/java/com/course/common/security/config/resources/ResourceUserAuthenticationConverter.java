package com.course.common.security.config.resources;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import com.course.common.core.utils.HuToolUtil;
import com.course.common.security.entity.Account;
import com.course.common.security.entity.CourseUser;
import com.course.common.security.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据check_token 的结果转化为用户对象
 * 
 * @author qinlei
 * @date 2021/8/12 下午10:28
 */
@Slf4j
public class ResourceUserAuthenticationConverter implements UserAuthenticationConverter {

	private static final String N_A = "N/A";
	// private ResourceServerProperties resourceServerProperties;

	// public ResourceUserAuthenticationConverter(ResourceServerProperties
	// resourceServerProperties) {
	// this.resourceServerProperties = resourceServerProperties;
	// }

	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put(USERNAME, authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			// 检查令牌的client_id，和资源服务器的client_id是否匹配
			// 如果不检查，不同的client_id的令牌都允许访问资源服务器
			// String clientId = (String) map.get("client_id");
			// if
			// (!this.resourceServerProperties.getClientId().equals(clientId)) {
			// throw new InvalidTokenException(clientId);
			// }
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			CourseUser courseUser = new CourseUser((String) map.get(USERNAME), N_A, true, true, true, true,
					authorities);
			// 复制扩展信息
			Account account = HuToolUtil.objectToBean(map, Account.class);
			SecurityUtils.copyObjToUser(account, courseUser);
			return new UsernamePasswordAuthenticationToken(courseUser, N_A, authorities);
		}
		return null;
	}

	/**
	 * 构建用户Authority权限
	 * 
	 * @param map
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(
					StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		return AuthorityUtils.NO_AUTHORITIES;
	}
}
