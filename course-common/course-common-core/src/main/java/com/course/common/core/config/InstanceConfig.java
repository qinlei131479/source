package com.course.common.core.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/6/28 下午4:12
 */
@Slf4j
@Data
@Component
public class InstanceConfig implements InitializingBean {
	private Integer mainFlag;
	private String dataIndex;
	private Date startupTime;
	/**
	 * 应用版本
	 */
	@Value("${instance.version:100}")
	private String version;
	/**
	 * 应用名称
	 */
	@Value("${spring.application.name}")
	private String appName;
	/**
	 * 应用端口
	 */
	@Value("${server.port:8080}")
	private String appPort;
	/**
	 * 实例ip
	 */
	private String hostAddress;
	/**
	 * 实例机器名
	 */
	private String hostName;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initInstance();
		log.warn(this.toString());
	}

	/**
	 * 初始化实例
	 */
	public void initInstance() {
		this.setStartupTime(new Date());
		try {
			this.setHostAddress(InetAddress.getLocalHost().getHostAddress());
			this.setHostName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			this.setHostAddress("127.0.0.1");
			this.setHostName("localhost");
		}
	}
}
