package com.course.design.singleton;

/**
 * 枚举单例
 * 
 * @author qinlei
 * @date 2021/6/3 下午4:02
 */
public enum EnumSingleton {
	INSTANCE;

	public void talk() {
		System.out.println("This is an EnumSingleton " + this.hashCode());
	}
}
