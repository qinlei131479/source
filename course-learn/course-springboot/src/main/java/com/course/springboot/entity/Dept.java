package com.course.springboot.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.entity.Req;

/**
 * 实体类：部门表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dept extends Req<Dept> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long parentId;
    
	private String ancestors;
    
	private String name;
    
	private Integer weight;
    
	private String leader;
    
	private String phone;
    
	private String email;
    
	private Integer status;
    
	private Integer isDelete;
    
	private String createBy;
    
	private LocalDateTime createTime;
    
	private String updateBy;
    
	private LocalDateTime updateTime;
}
