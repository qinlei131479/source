package com.course.mybatis.resource.utils;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:40
 */
public class ReflectUtils {

    public static Class<?> resolveType(String parameterType) {
        try {
            Class<?> clazz = Class.forName(parameterType);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
