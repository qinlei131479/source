package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.core.entity.Req;

/**
 * 实体类：附件表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Attach extends Req<Attach> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long ownerId;
    
	private Long refId;
    
	private String attachType;
    
	private String attachListJson;
    
	private String width;
    
	private String height;
    
	private String title;
    
	private String bucketName;
    
	private String url;
    
	private String link;
    
	private Long createUserId;
    
	private Date createTime;
    
	private Long updateUserId;
    
	private Date updateTime;
    
	private Long length;
    
	private String sign;
    
	private Integer sourceWidth;
    
	private Integer sourceHeight;
    
	private String description;
    
	private Integer weight;
    
	private Integer ossStatus;
    
	private String weixinMediaId;
    
	private Long duration;
    
	private String videoUrl;
}
