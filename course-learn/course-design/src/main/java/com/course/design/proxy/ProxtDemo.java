package com.course.design.proxy;

import com.course.design.proxy.factory.AopProxy;
import com.course.design.proxy.factory.CgLibProxyFactory;
import com.course.design.proxy.factory.JDKProxyFactory;
import com.course.design.proxy.obj.UserService;
import com.course.design.proxy.obj.UserServiceImpl;

/**
 * @author qinlei
 * @date 2021/6/3 下午3:22
 */
public class ProxtDemo {

	public static void main(String[] args) {
		// testJdk();
		// testJDKProxy2();
		testCgLibProxy();
	}

	public static void testJdk() {
		// 1、创建目标对象
		UserService service = new UserServiceImpl();
		// 3、调用目标对象的方法
		service.saveUser();
		System.out.println("===============");

		// 2、生成代理对象
		JDKProxyFactory proxyFactory = new JDKProxyFactory();
		// 得到代理对象
		UserService proxy = (UserService) proxyFactory.getProxy(service);

		// 生成class文件
		// generatorClass(proxy);
		// 4、调用代理对象的方法
		proxy.saveUser();
	}

	public static void testJDKProxy2() {
		// 1、创建目标对象
		UserService service = new UserServiceImpl();
		// 2、生成代理对象
		AopProxy proxyFactory = new AopProxy(service);
		// 得到代理对象
		UserService proxy = (UserService) proxyFactory.getProxy();

		// 3、调用目标对象的方法
		service.saveUser();
		System.out.println("===============");
		// 4、调用代理对象的方法
		proxy.saveUser();
	}

	public static void testCgLibProxy() {

		// 创建目标对象
		UserService service = new UserServiceImpl();
		// 调用目标对象的方法
		service.saveUser();
		// System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D://");
		// 生成代理对象
		CgLibProxyFactory proxyFactory = new CgLibProxyFactory();
		UserService proxy = (UserService) proxyFactory.getProxyByCgLib(service.getClass());

		System.out.println("===============");
		// 调用代理对象的方法
		proxy.saveUser();
	}
}
