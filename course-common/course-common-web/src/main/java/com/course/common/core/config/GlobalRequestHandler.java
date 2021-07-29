package com.course.common.core.config;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.course.common.core.enums.RequestAttrEnum;
import com.course.common.core.utils.HuToolUtil;
import com.course.common.core.utils.RequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局入参处理,配合GlobalHttpMessageConverter处理参数
 * 
 * @author qinlei
 * @date 2021/6/23 下午10:14
 */
@Slf4j
@ControllerAdvice
public class GlobalRequestHandler implements RequestBodyAdvice {
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return methodParameter.getParameterAnnotation(RequestBody.class) != null;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	/**
	 * 当且仅当参数为空是，执行以下方法
	 * 
	 * @param body
	 * @param inputMessage
	 * @param parameter
	 * @param targetType
	 * @param converterType
	 * @return
	 */
	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		RequestUtil.setAttr(RequestAttrEnum.bodyString, null);
		body = HuToolUtil.jsonStringToBean("{}", targetType.getTypeName());
		RequestUtil.setAttr(RequestAttrEnum.bodyObject, body);
		return body;

	}
}
