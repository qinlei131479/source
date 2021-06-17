package com.course.design.singleton;

/**
 * 单例模式
 * 同时在内存中，只有一个对象存在。
 * 单例模式有两种实现方式： 1：懒汉式(延迟加载) 2：饿汉式
 *
 * @author qinlei
 * @date 2021/6/3 下午3:50
 */
public class Student1 {

	/**
	 * 2：成员变量初始化本身对象
	 */
	private static Student1 student = new Student1();

	/**
	 * 1.构造私有
	 */
	private Student1() {
	}

	/**
	 * 3：对外提供公共方法获取对象
	 * 
	 * @return
	 */
	public static Student1 getSingletonInstance() {
		return student;
	}

	public void sayHello(String name) {
		System.out.println("hello," + name);
	}
}
