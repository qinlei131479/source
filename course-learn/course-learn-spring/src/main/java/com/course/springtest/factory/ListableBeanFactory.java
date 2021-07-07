package com.course.springtest.factory;

import java.util.List;

/**
 * 可以对Spring容器工厂进行批量操作
 * @author qinlei
 * @date 2021/6/9 下午12:17
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 获取指定类型的所有子类的bean实例集合
     *
     * @param clazz
     * @return
     */
    <T> List<T> getBeansByType(Class<T> clazz);
}
