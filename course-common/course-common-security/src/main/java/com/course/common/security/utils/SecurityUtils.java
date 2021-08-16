package com.course.common.security.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.course.common.security.entity.CourseUser;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;

/**
 * @author qinlei
 * @date 2021/8/12 下午10:36
 */
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public static CourseUser getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户
	 * 
	 * @param authentication
	 * @return
	 */
	public static CourseUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof CourseUser) {
			return (CourseUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public static Long getUserId() {
		return getUserId(getUser());
	}

	/**
	 * 获取用户id
	 * 
	 * @param user
	 * @return
	 */
	public static Long getUserId(CourseUser user) {
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	/**
	 * 复制obj到CourseUser的非静态字段
	 *
	 * @param obj
	 * @param courseUser
	 */
	public static void copyObjToUser(Object obj, Object courseUser) {
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
