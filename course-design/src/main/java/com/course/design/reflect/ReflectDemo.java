package com.course.design.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射获取构造方法，并且创建对象
 * 如何获取一个类的构造方法呢？
 * Class的成员方法
 *   public Constructor<?>[] getConstructors() :返回该类的所有公共构造方法
 *   public Constructor<?>[] getDeclaredConstructors() :返回该类的所有构造方法
 *   public Constructor<T> getConstructor(Class<?>... parameterTypes) :返回该类的一个公共构造方法
 *
 *   Constructor的成员方法
 *   public Object newInstance(Object... initargs)
 *
 * @author qinlei
 * @date 2021/6/3 下午2:53
 */
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		// a) 通过对象获取
		Student s1 = new Student();
		Class c1 = s1.getClass();

		Student s2 = new Student();
		Class c2 = s2.getClass();
		// false
		System.out.println(s1 == s2);
		// true
		System.out.println(c1 == c2);

		//b）通过静态属性获取
		Class c3 = Student.class;
		System.out.println(c1 == c3);

		//c）通过静态方法获取，使用字符串表示的类名
		Class c4 = Class.forName("com.course.design.reflect.Student");
		System.out.println(c1 == c4);
		//获取Student类的构造方法
		//Constructor[] constructors = c.getConstructors();
		Constructor[] constructors = c4.getDeclaredConstructors();
		// Constructor类中的方法
		// 		String getName()
		for (Constructor constructor : constructors) {
			System.out.println(constructor.toString());
		}
		System.out.println("=================================");
		Constructor constructor = c4.getDeclaredConstructor(String.class);
		System.out.println(constructor.toString());

		System.out.println("=======================");
		//暴力访问
		//Object object = constructor.newInstance("张三",40,"北京");

		constructor.setAccessible(true);
		Object object = constructor.newInstance("张三");
		System.out.println(object);

		System.out.println("=======================");
		//获取该类所有变量
		Field[] fields = c4.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.toString());
		}

		System.out.println("=======================");
		//会将父类的公共方法都获取到
//		Method[] methods1 = c4.getMethods();
//		for (Method method : methods1) {
//			System.out.println(method);
//		}
//		System.out.println("========================");
		//只会获取本类内的方法
		Method[] methods = c4.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
		System.out.println("==========================");
		Method method3 = c4.getDeclaredMethod("method3", String.class);
		System.out.println(method3);
		//使用Method方法
		Constructor constructorM = c4.getConstructor();
		Object objectM = constructorM.newInstance();
		System.out.println(objectM);
		//调用无参无返回值的
		Object result = method3.invoke(object,"zhangsan");

		System.out.println(result);
	}
}
