package com.course.design.factory.method;

import com.course.design.factory.method.factory.MobileFactory;
import com.course.design.factory.method.factory.PadFactory;
import com.course.design.factory.method.factory.ProductFactory;
import com.course.design.factory.pojo.Mobile;
import com.course.design.factory.pojo.Pad;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:11
 */
public class FactoryMethodDemo {
	public static void main(String[] args) {

		ProductFactory mobileFactory = new MobileFactory();
		Mobile mobile = (Mobile) mobileFactory.createProduct();
		mobile.use();

        ProductFactory padFactory = new PadFactory();
        Pad pad = (Pad) padFactory.createProduct();
        pad.use();
	}
}
