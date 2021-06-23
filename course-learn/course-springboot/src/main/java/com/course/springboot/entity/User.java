package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.entity.Req;

/**
 * 实体类：用户信息表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Req<User> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long deptId;
    
	private String name;
    
	private String nickName;
    
	private String type;
    
	private String email;
    
	private String phone;
    
	private Integer sex;
    
	private String avatar;
    
	private String password;
    
	private Integer status;
    
	private Integer isDelete;
    
	private String loginIp;
    
	private Date loginTime;
    
	private String createBy;
    
	private Date createTime;
    
	private String updateBy;
    
	private Date updateTime;
    
	private String remark;
}
