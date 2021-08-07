package com.course.common.security.propertites;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 资源服务器忽略的url
 * 
 * @author qinlei
 * @date 2021/8/7 下午3:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class PermitAllUrlProperties {

	private List<String> urls = new ArrayList<>();
}
