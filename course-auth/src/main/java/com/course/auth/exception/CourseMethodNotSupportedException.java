package com.course.auth.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 无权限访问该方法
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:47
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseMethodNotSupportedException extends CourseOAuth2Exception {

	public CourseMethodNotSupportedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "method_not_allowed";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.METHOD_NOT_ALLOWED.value();
	}
}
