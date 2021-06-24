package com.course.common.entity;

import java.lang.annotation.Annotation;

import lombok.Data;

/**
 * AOP 参数验证
 * 
 * @author qinlei
 * @date 2021/6/24 下午5:16
 */
@Data
public class ValidArg {

	private Annotation annotation;
	private Object arg;
}
