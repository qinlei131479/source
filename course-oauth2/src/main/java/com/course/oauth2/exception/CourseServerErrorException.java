package com.course.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 服务异常
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:47
 */
@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class CourseServerErrorException extends CourseOAuth2Exception {

	public CourseServerErrorException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "server_error";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}
}
