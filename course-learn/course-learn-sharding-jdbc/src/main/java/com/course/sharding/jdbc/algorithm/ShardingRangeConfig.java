package com.course.sharding.jdbc.algorithm;

import java.util.List;

import lombok.Data;

/**
 * 分配策略实体类
 * 
 * @author qinlei
 * @date 2021/7/7 下午12:34
 */
@Data
public class ShardingRangeConfig {

	private long start;

	private long end;

	private List<String> datasourceList;

}
