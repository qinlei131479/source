package com.course.design.singleton;

/**
 * 双重检查加锁
 * 
 * @author qinlei
 * @date 2021/6/3 下午3:59
 */
public class Student5 {

	private volatile static Student5 student;

	private Student5() {
	}

	public static Student5 getSingletonInstance() {
		if (student == null) {
			// B线程检测到student不为空
			synchronized (Student5.class) {
				if (student == null) {
					student = new Student5();
					// A线程被指令重排了，刚好先赋值了；但还没执行完构造函数。
				}
			}
		}
		// 后面B线程执行时将引发：对象尚未初始化错误。
		return student;
	}
}
