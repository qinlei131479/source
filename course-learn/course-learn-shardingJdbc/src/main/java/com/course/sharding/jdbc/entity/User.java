package com.course.sharding.jdbc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.course.common.core.entity.Req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Req<User> {

	private Long id;
	private Long corpId;
	private Date createTime;
	private Integer isDelete;
	private Date updateTime;
	private String avatar;
	private String deptArray;
	private Long deptId;
	private String flag;
	private String isAdmin;
	private String name;
	private String openId;
	@TableField(fill = FieldFill.INSERT)
	private String password;

	private String phone;
	private String post;
	private String username;
	private String workUnit;
	private String isManager;
	private Integer isDeptHead;

}
