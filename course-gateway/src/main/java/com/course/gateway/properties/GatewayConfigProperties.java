package com.course.gateway.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author qinlei
 * @date 2021/7/29 下午4:06
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfigProperties {

	/**
	 * 网关解密登录前端密码 秘钥 {@link com.course.gateway.filter.PasswordDecoderFilter}
	 */
	private String encodeKey;

	/**
	 * 网关不需要校验验证码的客户端 {@link com.course.gateway.filter.ValidateCodeFilter}
	 */
	private List<String> ignoreClients;
	/**
	 * 验证码宽度{@link com.course.gateway.handler.ImageCodeHandler}
	 */
	private Integer imageWidth = 100;
	/**
	 * 验证码高度{@link com.course.gateway.handler.ImageCodeHandler}
	 */
	private Integer imageHeigth = 40;
	/**
	 * 验证码过期时间（单位：秒）{@link com.course.gateway.handler.ImageCodeHandler}
	 */
	private Integer timeout = 60;
	/**
	 * 开启验证码校验
	 */
	private Boolean codeValidate = false;

}
