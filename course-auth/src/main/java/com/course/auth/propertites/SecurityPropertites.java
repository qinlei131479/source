package com.course.auth.propertites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * security 属性配置
 * 
 * @author qinlei
 * @date 2021/7/31 下午10:37
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityPropertites {
	/**
	 * TokenStore采用JWT是需要配置，默认course
	 */
	private String jwtSigningKey = "course";
}
