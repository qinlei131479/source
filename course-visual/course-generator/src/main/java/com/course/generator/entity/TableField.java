package com.course.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.course.common.entity.Req;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表字段
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableField extends Req<TableField> {
	@TableId(type = IdType.INPUT)
	private Long id;
	private String field;
	private String type;
	private String dbName;
	private String tableName_pre;
	private String className;
	private String tableNameLike;
}
