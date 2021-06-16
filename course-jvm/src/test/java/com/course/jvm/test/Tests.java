package com.course.jvm.test;

import com.course.jvm.test.model.Father;
import com.course.jvm.test.model.Son;

/**
 * jvm 测试
 * 
 * @author qinlei
 * @date 2021/6/11 下午1:12
 */
public class Tests {

	public static void main(String[] args) {
		// String s1 = new String("1");
		// s1.intern();
		// String s2 = "1";
		// // 结果false，因为"1"对象在intern已经存在，s1.intern()其实没有效果
		// System.out.println(s1 == s2);
		// String s3 = new String("1") + new String("1");
		// s3.intern();
		// String s4 = "11";
		// // 结果true
		// System.out.println(s3 == s4);


		Father father = new Son();
		father.f1('c');
	}

}
