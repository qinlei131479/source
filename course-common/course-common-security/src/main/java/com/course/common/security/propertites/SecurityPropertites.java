package com.course.common.security.propertites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import com.course.common.security.enums.TokenStoreTypeEnum;

import lombok.Data;

/**
 * security 属性配置
 *
 * @author qinlei
 * @date 2021/7/31 下午10:37
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2")
public class SecurityPropertites {
	/**
	 * TokenStore采用JWT是需要配置，默认course
	 */
	private String jwtSigningKey = "course";
	/**
	 * tokenStore存储方式，默认jwt
	 */
	private TokenStoreTypeEnum tokenStoreType = TokenStoreTypeEnum.redis;

	/**
	 * 客户端id
	 */
	private String clientId = "test";
	/**
	 * 客户端秘钥
	 */
	private String clientSecret = "$2a$10$IKx0VHAJ7OX4hrZybz8KZuik4ErGM.wCJK5u25EP7P.UA0KyfG8.e";

	/**
	 * 校验token远程地址
	 */
	private String tokenInfoUrl = "http://course-auth/oauth/check_token";

}
