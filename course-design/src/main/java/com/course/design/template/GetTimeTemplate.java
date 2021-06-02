package com.course.design.template;

/**
 * 模板设计模式 抽取一个抽象模板类，同时定义模板方法 对于模板方法的实现，在子类中去实现
 * 
 * @author qinlei
 * @date 2021/6/2 下午6:22
 */
public abstract class GetTimeTemplate {

	/**
	 * 固定流程方法
	 * 
	 * @return
	 */
	public long getTime() {
		// 获取起始时间
		long t1 = System.currentTimeMillis();

		// 模板方法
		code();

		// 获取结束时间
		long t2 = System.currentTimeMillis();
		return t2 - t1;
	}

	/**
	 * 钩子方法
	 */
	public abstract void code();
}
