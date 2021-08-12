package com.course.common.security.entity;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 账号信息
 * 
 * @author qinlei
 */
@Data
public class Account implements Serializable {
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户扩展信息
	 */
	private Map<String, Object> user;
	/**
	 * 登录账号
	 */
	private String userAccount;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 权限标识集合
	 */
	private String[] permissions;
	/**
	 * 角色集合
	 */
	private Integer[] roles;
	/**
	 * 状态
	 */
	private Integer status;

}
