package com.course.oauth2.exception;

import com.course.common.core.enums.ResCommonEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * oauth2异常格式化
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:37
 */
@Slf4j
public class OAuth2ExceptionSerializer extends StdSerializer<CourseOAuth2Exception> {

	protected OAuth2ExceptionSerializer() {
		super(CourseOAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(CourseOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		log.error("value = {}", value);
		gen.writeStartObject();
		gen.writeObjectField("code", ResCommonEnum.FAIL.getCode());
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("obj", value.getErrorCode());
		gen.writeEndObject();
	}
}
