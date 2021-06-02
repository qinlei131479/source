package com.course.design.factory.simple.factory;

import java.util.HashMap;
import java.util.Map;

import com.course.design.factory.pojo.Mobile;
import com.course.design.factory.pojo.Pad;
import com.course.design.factory.pojo.Product;

/**
 * 简单工厂模式
 *
 * @author qinlei
 * @date 2021/6/2 下午6:03
 */
public class ProductFactory {

	/**
	 * 简单工厂设计模式（负担太重、不符合开闭原则）
	 * 
	 * @param name
	 * @return
	 */
	public static Product createProduct(String name) {
		if ("mobile".equals(name)) {
			return new Mobile();
		} else if ("pad".equals(name)) {
			return new Pad();
		} else {
			return null;
		}
	}

	public static Object createObject(String name) {
		if ("mobile".equals(name)) {
			return new Mobile();
		} else if ("pad".equals(name)) {
			return new Pad();
		} else {
			return null;
		}
	}

	public static Object getBean(String name) {
		// 优化方案
		// 给对象起个名，在xml配置文件中，建立名称和对象的映射关系
		// Map中的数据怎么来？
		Map<String, Object> map = new HashMap<>();
		Object object = map.get(name);
		return object;
	}
}
