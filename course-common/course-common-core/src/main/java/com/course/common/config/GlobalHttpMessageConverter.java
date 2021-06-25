package com.course.common.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.course.common.constant.CommonConstants;
import com.course.common.entity.Req;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * controller类，请求和返回，json转换<br>
 * 
 * 执行顺序如下： <br>
 * 1、参数为空： GlobalRequestHandler#supports#handleEmptyBody-》controller
 * -》CourseHttpMessageConverter writeInternal
 * 
 * 2、参数非空：GlobalRequestHandler#supports#beforeBodyRead
 * -》GlobalHttpMessageConverter#readInternal
 * -》GlobalRequestHandler#afterBodyRead -》controller
 * -》CourseHttpMessageConverter#writeInternal
 * 
 * @author qinlei
 * @date 2021/6/24 下午2:29
 */
@Slf4j
@Component
public class GlobalHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	}

	/**
	 * 只有当这个方法的返回true，<br>
	 * 若参数传递为空，则优先执行 GlobalRequestHandler#handleEmptyBody<br>
	 * 否则将执行 CourseHttpMessageConverter#readInternal
	 * 
	 * @param clazz
	 * @param mediaType
	 * @return
	 */
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return Req.class.isAssignableFrom(clazz);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i;
		while ((i = inputMessage.getBody().read()) != -1) {
			baos.write(i);
		}
		String bodyString = baos.toString(CommonConstants.UTF8);
		// RequestUtil.setAttr(RequestAttrEnum.bodyString, bodyString);
		Object requestBodyObject = null;
		if (StrUtil.isNotBlank(bodyString)) {
			bodyString = bodyString.trim();
			if (bodyString.indexOf(CommonConstants.XML_PRE) != 0) {
				// 非xml，按照json处理
				try {
					requestBodyObject = JSONUtil.toBean(bodyString, clazz);
				} catch (Exception e) {
					log.error("readInternal,body解析失败：{}", bodyString);
				}
			}
		}
		if (requestBodyObject == null) {
			requestBodyObject = JSONUtil.toBean("{}", clazz);
		}
		// RequestUtil.setAttr(RequestAttrEnum.bodyObject, requestBodyObject);
		return requestBodyObject;
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String result = "";
		if (o != null) {
			if (o.getClass().equals(String.class)) {
				result = o.toString();
			} else {
				result = JSONUtil.toJsonStr(o);
			}
		}
		outputMessage.getBody().write(result.getBytes(CommonConstants.UTF8));
	}
}