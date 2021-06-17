package com.course.springboot.config;

/**
 * 统一返回
 * 
 * @author qinlei
 * @date 2021/6/16 下午5:34
 */
public class ErrorResponse {

	private int code;

	private String message;

	public ErrorResponse(int code, String message) {
		this.code = code;
		this.message = message;

	}
}
