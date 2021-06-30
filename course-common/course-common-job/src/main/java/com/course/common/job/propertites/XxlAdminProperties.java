package com.course.common.job.propertites;

import lombok.Data;

/**
 * 调度中心注册配置
 * 
 * @author qinlei
 * @date 2021/6/30 下午4:50
 */
@Data
public class XxlAdminProperties {

	/**
	 * 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。
	 * 执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
	 */
	private String addresses = "http://styn-job:9350/xxl-job-admin";
}
