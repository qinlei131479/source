package com.course.auth.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.common.security.entity.CourseUser;

import lombok.RequiredArgsConstructor;

/**
 * 注入自定义UserDetailsService实现
 * 
 * @author qinlei
 * @date 2021/8/2 下午9:29
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
		return new CourseUser(username, passwordEncoder.encode("123456"), true, true, true, true, authorities);
	}
}
