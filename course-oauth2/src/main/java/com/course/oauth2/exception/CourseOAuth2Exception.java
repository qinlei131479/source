package com.course.oauth2.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

/**
 * 自定义顶级异常 OAuth2Exception
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:35
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseOAuth2Exception extends OAuth2Exception {

	@Getter
	private String errorCode;

	public CourseOAuth2Exception(String msg) {
		super(msg);
	}

	public CourseOAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

}
