package com.course.sharding.jdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 部门
 * 
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Data
@ToString
@Builder
@TableName("department")
@EqualsAndHashCode(callSuper = false)
public class Department extends Model<Department> {

	private Long id;
	private Long deptId;
	private Long corpId;
	private String createTime;
	private Integer isDelete;
	private String updateTime;
	private String code;
	private String deptArray;
	private Integer level;
	private String name;
	private Integer num;
	private String parentId;
	private String pathIds;
	private String pathNames;
	private String remark;
	private String contact;
	private String phone;
	private Integer assign;

}
