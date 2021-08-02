package com.course.common.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
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

	public CourseUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}
