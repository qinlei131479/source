package com.course.auth.exception;

import com.course.auth.config.OAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 未认证异常
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:42
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseAuthenticationException extends CourseOAuth2Exception {

	public CourseAuthenticationException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "unauthorized";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}

}
