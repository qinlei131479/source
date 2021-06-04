package com.course.springtest.pojo;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinlei
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User {

	/**
	 * 用户ID
	 */
	private Long id;

	/**
	 * 部门ID
	 */
	private Long deptId;

	/**
	 * 用户账号
	 */
	private String name;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户类型（00系统用户）
	 */
	private String type;

	/**
	 * 用户邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 用户性别（0男 1女 2未知）
	 */
	private Integer sex;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	private Integer status;

	/**
	 * 删除标志（0代表存在 1代表删除）
	 */
	private Integer isDelete;

	/**
	 * 最后登陆IP
	 */
	private String loginIp;

	/**
	 * 最后登陆时间
	 */
	private Date loginTime;

	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * toString方法
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", type=" + type + ", sex=" + sex + ", email=" + email
				+ "]";
	}

}
