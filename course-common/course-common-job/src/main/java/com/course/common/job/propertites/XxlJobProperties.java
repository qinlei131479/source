package com.course.common.job.propertites;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * xxl-job 配置
 * 
 * @author qinlei
 * @date 2021/6/30 下午4:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties implements InitializingBean, EnvironmentAware {

	private Environment environment;

	private XxlAdminProperties admin = new XxlAdminProperties();

	private XxlExecutorProperties executor = new XxlExecutorProperties();

	@Override
	public void afterPropertiesSet() {
		// 若是没有设置appname 则取 application Name
		if (StrUtil.isEmpty(executor.getAppname())) {
			executor.setAppname(environment.getProperty("spring.application.name"));
		}
	}

	/**
	 * Set the {@code Environment} that this component runs in.
	 */
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
