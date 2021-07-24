package com.course.design.delegate;

/**
 * 委托者模式
 * 
 * @author qinlei
 * @date 2021/6/2 下午9:15
 */
public class DelegateTest {

	public static void main(String[] args) {
		ProjectManager pm = new ProjectManager();
		pm.doSomething();
	}
}
