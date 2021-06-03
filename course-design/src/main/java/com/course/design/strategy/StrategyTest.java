package com.course.design.strategy;

import com.course.design.strategy.abs.AirPlanelStrategy;
import com.course.design.strategy.abs.BicycleStrategy;
import com.course.design.strategy.abs.TrainStrategy;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:28
 */
public class StrategyTest {

	public static void main(String[] args) {
		// 环境类
		PersonContext personContext = new PersonContext();
		// 太远了，需要做飞机
		personContext.travel(1000);
		// 不太远，飞机太贵，选择火车
		personContext.travel(300);
		// 很近，直接选择自行车
		personContext.travel(5);


		// 环境类
		AirPlanelStrategy airPlanelStrategy = new AirPlanelStrategy();
		PersonContext2 person = new PersonContext2(airPlanelStrategy);
		// 太远了，需要做飞机
		person.travel();

		// 环境类
		TrainStrategy trainStrategy = new TrainStrategy();
		person = new PersonContext2(trainStrategy);
		// 太远了，需要做飞机
		person.travel();

		// 环境类
		BicycleStrategy bicycleStrategy = new BicycleStrategy();
		person = new PersonContext2(bicycleStrategy);
		// 太远了，需要做飞机
		person.travel();
	}
}
