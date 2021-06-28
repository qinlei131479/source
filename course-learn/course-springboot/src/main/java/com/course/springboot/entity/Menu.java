package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.entity.Req;

/**
 * 实体类：菜单表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends Req<Menu> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long parentId;
    
	private String name;
    
	private String path;
    
	private String icon;
    
	private Integer needPowerFlag;
    
	private Integer weight;
    
	private Long createUserId;
    
	private Date createTime;
    
	private Long updateUserId;
    
	private Date updateTime;
}
