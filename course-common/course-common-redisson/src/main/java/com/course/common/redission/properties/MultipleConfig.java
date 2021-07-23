package com.course.common.redission.properties;

import java.util.ArrayList;
import java.util.List;

import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;

import lombok.Data;

/**
 * 多节点配置：哨兵、集群、主从、云部署
 * 
 * @author qinlei
 * @date 2021/7/23 上午10:48
 */
@Data
public class MultipleConfig {
	/**
	 * 服务器节点地址.必填
	 */
	private List<String> nodes = new ArrayList();
	/**
	 * (哨兵模式特有)主服务器的名称是哨兵进程中用来监测主从服务切换情况的
	 */
	private String masterName;
	/**
	 * 集群,哨兵,云托管：对Redis集群节点状态扫描的时间间隔。单位是毫秒
	 */
	private Integer scanInterval = 1000;
	/**
	 * 在多Redis服务节点的环境里，可以选用以下几种负载均衡方式选择一个节点：
	 * org.redisson.connection.balancer.WeightedRoundRobinBalancer - 权重轮询调度算法
	 * org.redisson.connection.balancer.RoundRobinLoadBalancer - 轮询调度算法
	 * org.redisson.connection.balancer.RandomLoadBalancer - 随机调度算法
	 */
	private String loadBalancer = "org.redisson.connection.balancer.RoundRobinLoadBalancer";
	/**
	 * 设置读取操作选择节点的模式。 <br>
	 * 可用值为： SLAVE - 只在从服务节点里读取。 MASTER - 只在主服务节点里读取。 MASTER_SLAVE -
	 * 在主从服务节点里都可以读取。
	 */
	private ReadMode readMode = ReadMode.SLAVE;
	/**
	 * 设置订阅操作选择节点的模式。 <br>
	 * 可用值为： SLAVE - 只在从服务节点里订阅。 MASTER - 只在主服务节点里订阅。
	 */
	private SubscriptionMode subscriptionMode = SubscriptionMode.SLAVE;
	/**
	 * 多从节点的环境里，每个从服务节点里用于普通操作（非发布和订阅）的最小保持连接数（长连接）。<br>
	 * 长期保持一定数量的连接有利于提高瞬时读取反映速度。
	 */
	private Integer slaveConnectionMinIdleSize = 32;
	/**
	 * 多从节点的环境里，每个从服务节点里用于普通操作（非发布和订阅）连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
	 */
	private Integer slaveConnectionPoolSize = 64;
	/**
	 * 多节点的环境里，每个主节点的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度。
	 */
	private Integer masterConnectionMinIdleSize = 32;
	/**
	 * 多主节点的环境里，每个主节点的连接池最大容量。连接池的连接数量自动弹性伸缩。
	 */
	private Integer masterConnectionPoolSize = 64;


}
