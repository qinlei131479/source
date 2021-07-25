package com.course.biz.sys.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.course.common.core.entity.Req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类：应用接口表
 *
 * @author qinlei
 * @date 2021/06/28 12:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Api extends Req<Api> {
	@TableId(type = IdType.INPUT)
	private Long id;

	private String platform;

	private String name;

	private String description;

	private String path;

	private String req;

	private String res;

	private Long createUserId;

	private Date createTime;

	private Long updateUserId;

	private Date updateTime;

	private String config;

	private Integer needLoginFlag;

	private Integer needPowerFlag;

	private Integer needLogFlag;

	private Integer status;

	/**
	 * 多个action
	 */
	@TableField(exist = false)
	private String actions;
	/**
	 * 同步更新接口、菜单、菜单接口权限
	 */
	@TableField(exist = false)
	private List<Menu> menuList;
	@TableField(exist = false)
	private List<Api> apiList;
	@TableField(exist = false)
	private List<MenuApi> menuApiList;
	@TableField(exist = false)
	private String version;
}
