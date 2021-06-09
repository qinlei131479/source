package com.course.springtest.aware;

import com.course.springtest.factory.BeanFactory;

/**
 * @author qinlei
 * @date 2021/6/9 下午12:23
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
