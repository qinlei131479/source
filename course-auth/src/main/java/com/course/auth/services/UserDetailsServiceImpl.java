package com.course.auth.services;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.course.common.core.constant.SecurityConstants;
import com.course.common.core.entity.Res;
import com.course.common.core.enums.FlagEnum;
import com.course.common.security.entity.Account;
import com.course.common.security.entity.CourseUser;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = new Account();
		user.setUserId(997L);
		user.setStatus(FlagEnum.no.getCode());
		user.setUserName(username);
		user.setPassword("$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC");
		Res<Account> res = Res.succ(user);
		if (res == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		Account account = res.getData();
		CourseUser courseUser = new CourseUser(account.getUserName(), SecurityConstants.BCRYPT + account.getPassword(),
				FlagEnum.checkNo(account.getStatus()));
		copyObjToStynUser(account, courseUser);
		return courseUser;
	}

	/**
	 * 复制obj到CourseUser的非静态字段
	 * 
	 * @param obj
	 * @param courseUser
	 */
	public void copyObjToStynUser(Object obj, Object courseUser) {
		Field[] list = ReflectUtil.getFieldsDirectly(courseUser.getClass(), false);
		if (ArrayUtil.isNotEmpty(list)) {
			for (Field field : list) {
				boolean isStatic = Modifier.isStatic(field.getModifiers());
				if (isStatic == false) {
					ReflectUtil.setFieldValue(courseUser, field.getName(),
							ReflectUtil.getFieldValue(obj, field.getName()));
				}
			}
		}
	}
}
