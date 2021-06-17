package com.course.design.proxy.factory;

import java.lang.reflect.Proxy;

import com.course.design.proxy.obj.UserService;

/**
 * 主要作用就是生成代理类 使用JDK的动态代理实现 它是基于接口实现的
 * 
 * @author qinlei
 * @date 2021/6/3 下午3:26
 */
public class JDKProxyFactory {

	/**
	 * @return
	 */
	public Object getProxy(Object target) {

		Class<?> clazz = UserService.class;
		Class<?> clazz2 = target.getClass();
		System.out.println(clazz);
		System.out.println(clazz2);
		System.out.println(clazz2.getInterfaces());
		System.out.println(target.getClass().getInterfaces());

		// Proxy是JDK中的API类
		// 第一个参数：目标对象的类加载器
		// 第二个参数：目标对象的接口
		// 第二个参数：代理对象的执行处理器
		// Object proxy =
		// Proxy.newProxyInstance(target.getClass().getClassLoader(), new
		// Class[] { clazz2 },new MyInvocationHandler(target));
		Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new MyInvocationHandler(target));

		// Proxy.newProxyInstance(loader, interfaces, h);
		return proxy;
	}

}
