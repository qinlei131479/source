package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.core.entity.Req;

/**
 * 实体类：角色信息表
 *
 * @author qinlei
 * @date   2021/06/30 17:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends Req<Role> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private String name;
    
	private String roleKey;
    
	private Integer weight;
    
	private Integer dataScope;
    
	private Integer status;
    
	private Integer isDelete;
    
	private String createBy;
    
	private Date createTime;
    
	private String updateBy;
    
	private Date updateTime;
    
	private String remark;
}
