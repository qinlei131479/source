package com.course.design.factory.simple;

import com.course.design.factory.pojo.Mobile;
import com.course.design.factory.pojo.Pad;
import com.course.design.factory.simple.factory.ProductFactory;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:01
 */
public class SimpleFactoryDemo {

	public static void main(String[] args) {
		Mobile mobile = (Mobile) ProductFactory.createProduct("mobile");
		mobile.use();

		Pad pad = (Pad) ProductFactory.createProduct("pad");
		pad.use();
	}
}
