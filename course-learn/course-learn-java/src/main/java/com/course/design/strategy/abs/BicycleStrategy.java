package com.course.design.strategy.abs;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:32
 */
public class BicycleStrategy implements TravelStrategy {
	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：骑自行车");
	}

	@Override
	public boolean isOK(int type) {
		if (type <= 50) {
			return true;
		}
		return false;
	}
}
