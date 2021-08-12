package com.course.common.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

/**
 * 扩展登录用户
 * 
 * @author qinlei
 * @date 2021/8/2 下午1:10
 */
public class CourseUser extends User {

	@Getter
	@Setter
	private Long userId;
	@Getter
	@Setter
	private String userName;
	@Getter
	@Setter
	private Long deptId;

	public CourseUser(String username, String password, boolean accountNonLocked) {
		this(username, password, true, true, true, accountNonLocked, AuthorityUtils.createAuthorityList(new String[0]));
	}

	public CourseUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}
