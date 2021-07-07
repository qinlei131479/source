package com.course.design.proxy.factory;

import net.sf.cglib.proxy.Enhancer;

/**
 * 主要作用就是生成代理类 使用CGLib动态代理技术实现 它是基于继承的方式实现的
 * cglib底层是通过asm包去实现的字节码的编写
 * 
 * @author qinlei
 * @date 2021/6/3 下午3:29
 */
public class CgLibProxyFactory {

    public Object getProxyByCgLib(Class<?> clazz) {
        // 创建增强器
        Enhancer enhancer = new Enhancer();
        // 设置需要增强的类的类对象
        enhancer.setSuperclass(clazz);
        // 设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());
        // 获取增强之后的代理对象
        Object object = enhancer.create();

        return object;
    }
}
