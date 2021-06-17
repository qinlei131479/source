package com.course.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:32
 */
public class ReflectUtil {

    /**
	 * 装载一个类
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> resolveType(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用构造器创建bean的实例
	 *
	 * @param clazz
	 * @param args
	 * @return
	 */
	public static Object createObject(Class<?> clazz, Object... args) {
		try {
			// TODO 可以根据输入参数获取指定构造参数的构造方法
			Constructor<?> constructor = clazz.getConstructor();
			// 默认调用无参构造进行对象的创建
			return constructor.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反射方式设置对象属性
	 * 
	 * @param beanInstance
	 * @param fieldName
	 * @param value
	 */
	public static void setProperty(Object beanInstance, String fieldName, Object value) {
		try {
			Class<?> clazz = beanInstance.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(beanInstance, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字段类型
	 * 
	 * @param beanClassName
	 * @param name
	 * @return
	 */
	public static Class<?> getTypeByFieldName(String beanClassName, String name) {
		try {
			Class<?> clazz = resolveType(beanClassName);
			Field field = clazz.getDeclaredField(name);
			return field.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反射方式执行方法
	 * 
	 * @param beanInstance
	 * @param methodName
	 */
	public static void invokeMethod(Object beanInstance, String methodName) {
		try {
			if (methodName == null || "".equals(methodName)) {
				return;
			}
			Class<?> clazz = beanInstance.getClass();
			Method method = clazz.getDeclaredMethod(methodName);
			// 设置允许访问私有方法和变量，此处也称之为暴力破解
			method.setAccessible(true);
			method.invoke(beanInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
