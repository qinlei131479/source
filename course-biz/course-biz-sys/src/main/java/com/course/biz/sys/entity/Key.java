package com.course.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.course.common.core.entity.Req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局唯一ID生成实体
 * 
 * @author qinlei
 * @date 2021/6/25 下午5:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Key extends Req<Key> {

	@TableId(type = IdType.INPUT)
	private Long id;

	/**
	 * 是否执行
	 */
	@TableField(exist = false)
	private Integer enableFlag;
	/**
	 * 最小主键数量
	 */
	@TableField(exist = false)
	private Integer min;
	/**
	 * 最大主键数量
	 */
	@TableField(exist = false)
	private Integer max;
}
