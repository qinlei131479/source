package com.course.springboot.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.course.common.core.entity.Req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类：部门表
 *
 * @author qinlei
 * @date 2021/06/23 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dept extends Req<Dept> {
	@TableId(type = IdType.INPUT)
	private Long id;
	@NotNull(message = "不允许为空", groups = { Create.class, Update.class })
	private Long parentId;

	private String ancestors;
	@NotBlank(message = "不允许为空", groups = { Create.class, Update.class })
	private String name;
	@NotNull(message = "不允许为空", groups = { Create.class, Update.class })
	private Integer weight;

	private String leader;
	@NotBlank(message = "不允许为空", groups = { Create.class, Update.class })
	private String phone;

	private String email;
	@NotNull(message = "不允许为空", groups = { Create.class, Update.class })
	private Integer status;

	private Integer isDelete;

	private String createBy;

	private LocalDateTime createTime;

	private String updateBy;

	private LocalDateTime updateTime;
}
