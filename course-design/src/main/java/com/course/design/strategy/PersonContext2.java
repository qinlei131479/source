package com.course.design.strategy;

import com.course.design.strategy.abs.TravelStrategy;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:35
 */
public class PersonContext2 {

	/**
	 * 拥有一个出行策略引用
	 */
	private TravelStrategy travelStrategy;

	public PersonContext2(TravelStrategy travelStrategy) {
		this.travelStrategy = travelStrategy;
	}

	public void travel() {
		travelStrategy.travelWay();
	}
}
