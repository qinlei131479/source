package com.course.common.redission.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import com.course.common.redission.enums.LockModel;
import com.course.common.redission.enums.Model;

import lombok.Data;

/**
 * 配置redisson参数
 * 
 * @author qinlei
 * @date 2021/7/18 下午3:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 尝试连接redis数据库编号,默认编号0
	 */
	private Integer database = 0;
	/**
	 * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
	 */
	private Integer timeout = 3000;
	/**
	 * 超时（redisson超时报错）
	 */
	private Integer connectTimeout = 1000;
	/**
	 * 监控dog超时时间
	 */
	private Integer lockWatchdogTimeout = 30000;
	/**
	 * 集群模式:SINGLE(单例),SENTINEL(哨兵),MASTERSLAVE(主从),CLUSTER(集群),REPLICATED(云托管)
	 */
	private Model model = Model.SINGLE;
	/**
	 * Redisson的对象编码类是用于将对象进行序列化和反序列化，以实现对该对象在Redis里的读取和存储
	 */
	private String codec = "org.redisson.codec.JsonJacksonCodec";
	/**
	 * 这个线程池数量被所有RTopic对象监听器，RRemoteService调用者和RExecutorService任务共同共享
	 */
	private Integer threads = 8;
	/**
	 * 这个线程池数量是在一个Redisson实例内，被其创建的所有分布式数据类型和服务，以及底层客户端所一同共享的线程池里保存的线程数量
	 */
	private Integer nettyThreads = 8;

	/**
	 * 锁的模式:REENTRANT(可重入锁),FAIR(公平锁),MULTIPLE(联锁),REDLOCK(红锁),READ(读锁),
	 * WRITE(写锁)
	 */
	private LockModel lockModel;
	/**
	 * 如果尝试达到 retryAttempts（命令失败重试次数）仍然不能将命令发送至某个指定的节点时，将抛出错误。
	 * 如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时）计时
	 */
	private Integer retryAttempts = 3;
	/**
	 * 在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒
	 */
	private Integer retryInterval = 1500;
	/**
	 * 用于发布和订阅连接的最小保持连接数（长连接）。<br>
	 * Redisson内部经常通过发布和订阅来实现许多功能。长期保持一定数量的发布订阅连接是必须的。
	 */
	private Integer subscriptionConnectionMinIdleSize = 1;
	/**
	 * 用于发布和订阅连接的连接池最大容量。连接池的连接数量自动弹性伸缩
	 */
	private Integer subscriptionConnectionPoolSize = 50;

	/**
	 * 监测DNS的变化情况的时间间隔
	 */
	private Long dnsMonitorInterval = 5000L;
	/**
	 * 单机配置
	 */
	@NestedConfigurationProperty
	private SingleConfig singleConfig;
	/**
	 * 哨兵、集群、主从 配置，必须new对象，否则无法初始化
	 */
	@NestedConfigurationProperty
	private MultipleConfig multipleConfig;

}
