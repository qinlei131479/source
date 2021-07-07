package com.course.springboot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.core.entity.Req;

/**
 * 实体类：接口日志表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiLog extends Req<ApiLog> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long refId;
    
	private Long userId;
    
	private Long apiId;
    
	private String clientType;
    
	private String agent;
    
	private String reqAction;
    
	private String token;
    
	private String uri;
    
	private String url;
    
	private String req;
    
	private String res;
    
	private Integer resCode;
    
	private String resMsg;
    
	private Long handleTime;
    
	private Date createTime;
    
	private String ip;
    
	private String error;
    
	private String appVersion;
    
	private String appName;
    
	private String apiPlatform;
    
	private String pageTitle;
    
	private String pagePath;
    
	private String pageUrl;
    
	private String pageVersion;
    
	private String openid;
    
	private Integer fixFlag;
    
	private Long fixUserId;
    
	private String fixDesc;
    
	private Date fixTime;
    
	private String userName;
    
	private String userAccount;
}
