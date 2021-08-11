package com.course.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 无访问权限
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:46
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseAccessDeniedException extends CourseOAuth2Exception {

	public CourseAccessDeniedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "access_denied";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FORBIDDEN.value();
	}
}
