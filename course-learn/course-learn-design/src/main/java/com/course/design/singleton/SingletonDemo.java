package com.course.design.singleton;

/**
 * @author qinlei
 * @date 2021/6/3 下午4:00
 */
public class SingletonDemo {

	public static void main(String[] args) {
		// 饿汉式
		System.out.println(Student1.getSingletonInstance());
		// 懒汉式
		System.out.println(Student2.getSingletonInstance());
		System.out.println(Student3.getSingletonInstance());
		System.out.println(Student4.getSingletonInstance());
		System.out.println(Student5.getSingletonInstance());
		System.out.println(Student5.getSingletonInstance());
		// 枚举式
		System.out.println(EnumSingleton.INSTANCE);
		// 静态内部类式
		System.out.println(StaticSingleton.getSingletonInstance());
	}

}
