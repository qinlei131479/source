package com.course.design.template;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:21
 */
public class ForTemplate extends GetTimeTemplate {
	@Override
	public void code() {
		// 输出for循环
		for (int i = 0; i < 10000; i++) {
			System.out.println(i);
		}
	}
}
