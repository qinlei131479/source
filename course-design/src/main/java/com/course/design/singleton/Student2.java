package com.course.design.singleton;

/**
 * 单例实现之懒汉式实现 存在线程安全问题
 * 
 * @author qinlei
 * @date 2021/6/3 下午3:52
 */
public class Student2 {

	/**
	 * 1：构造私有
	 */
	private Student2() {
	}

	/**
	 * 2：定义私有静态成员变量，先不初始化
	 */
	private static Student2 student = null;

	/**
	 * 3：定义公开静态方法，获取本身对象
	 * 
	 * @return
	 */
	public static Student2 getSingletonInstance() {
		// 没有对象，再去创建
		if (student == null) {
			student = new Student2();
		}
		// 有对象就返回已有对象
		return student;
	}

}
