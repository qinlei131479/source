package com.course.oauth2.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 无效的访问
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:46
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseInvalidGrantException extends CourseOAuth2Exception {

	public CourseInvalidGrantException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 500;
	}
}
