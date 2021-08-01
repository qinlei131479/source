package com.course.common.security.propertites;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

