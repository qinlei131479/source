package com.course.design.delegate;

/**
 * 委托人
 * 
 * @author qinlei
 * @date 2021/6/2 下午9:14
 */
public class ProjectManager {

	/**
	 * 受托人
	 */
	private Developer delegate;

	public  ProjectManager(){
		delegate = new Developer();
	}

	public void doSomething() {
		// 根据需求，将实际工作派发给开发人员或者其他人员
		delegate.development();
	}
}
