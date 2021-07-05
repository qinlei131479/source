package com.course.shardingjdbc.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

/**
 * sharding-jdbc 分库分表测试
 * 
 * @author qinlei
 * @date 2021/7/5 下午3:36
 */
public class ShardingJdbcApiTest {

	/**
	 * create database
	 * 
	 * @param username
	 * @param password
	 * @param url
	 * @return
	 */
	public static DataSource createDataSource(String username, String password, String url) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		return dataSource;
	}

	/**
	 * do result
	 * 
	 * @param ds
	 * @param sql
	 * @param oid
	 * @param uid
	 * @param name
	 * @throws SQLException
	 */
	public static void execute(DataSource ds, String sql, int oid, int uid, String name) throws SQLException {
		Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setInt(1, oid);
		ps.setInt(2, uid);
		ps.setString(3, name);
		ps.execute();
	}

	public static void main(String[] args) {

		Map<String, DataSource> map = new HashMap<>();
		map.put("sj_ds_0", createDataSource("root", "iflash", "jdbc:mysql://localhost:3306/sj_ds_0?characterEncoding=UTF-8&useSSL=false"));
		map.put("sj_ds_1", createDataSource("root", "iflash", "jdbc:mysql://localhost:3306/sj_ds_1?characterEncoding=UTF-8&useSSL=false"));

		ShardingRuleConfiguration config = new ShardingRuleConfiguration();
		// 配置Order表规则
		TableRuleConfiguration orderRuleConfig = new TableRuleConfiguration();
		orderRuleConfig.setLogicTable("t_order");// 设置逻辑表.
		orderRuleConfig.setActualDataNodes("sj_ds_${0..1}.t_order_${0..1}");// 设置实际数据节点.
		orderRuleConfig.setKeyGeneratorColumnName("oid");// 设置主键列名称.

		// 配置Order表规则：配置分库 + 分表策略(这个也可以在ShardingRuleConfiguration进行统一设置)
		orderRuleConfig
				.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("uid", "sj_ds_${uid % 2}"));
		orderRuleConfig
				.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("oid", "t_order_${oid % 2}"));
		config.getTableRuleConfigs().add(orderRuleConfig);

		try {
			DataSource ds = ShardingDataSourceFactory.createDataSource(map, config, new HashMap(), new Properties());

			for (int i = 1; i <= 20; i++) {
				String sql = "insert into t_order(oid,uid,name) values(?,?,?)";
				execute(ds, sql, 30+i, i, i + "aaa");
			}
			System.out.println("数据插入完成。。。");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
