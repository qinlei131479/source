package com.course.common.security.propertites;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.course.common.security.enums.TokenStoreTypeEnum;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security 属性配置
 *
 * @author qinlei
 * @date 2021/7/31 下午10:37
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "course.security.oauth2")
public class SecurityPropertites {
	/**
	 * TokenStore采用JWT是需要配置，默认course
	 */
	private String jwtSigningKey = "course";
	/**
	 * 可通过的urlList
	 */
	private List<String> ignoreUrls = new ArrayList<>();
	/**
	 * tokenStore存储方式，默认jwt
	 */
	private TokenStoreTypeEnum tokenStoreType = TokenStoreTypeEnum.jwt;
	/**
	 * 客户端id
	 */
	private String clientId = "test";
	/**
	 * 客户端秘钥
	 */
	private String clientSecret = "test";
	/**
	 * 校验token远程地址
	 */
	private String checkTokenUrl = "http://localhost:8898/oauth/check_token";
	/**
	 * 令牌有效期，2小时 = 60 * 60 * 2
	 */
	private Integer accessTokenSeconds = 7200;
	/**
	 * 刷新令牌有效期: 1天 = 60 * 60 * 24
	 */
	private Integer refreshTokenSeconds = 86400;

}
