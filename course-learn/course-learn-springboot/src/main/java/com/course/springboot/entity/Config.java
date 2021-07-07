package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.course.common.core.entity.Req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类：参数配置表
 *
 * @author qinlei
 * @date 2021/06/23 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config extends Req<Config> {
	@TableId(type = IdType.INPUT)
	private Long id;

	private String name;

	private String code;

	private String value;

	private Integer isInner;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

	private String remark;
}
