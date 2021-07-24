package com.course.juc.jvm.model;

/**
 * @author qinlei
 * @date 2021/6/12 下午10:01
 */
public class Son extends Father{

    public void f1() { // 覆盖父类的方法
        System.out.println("Son-f1()");
    }

    public void f1(char c) {
        System.out.println("Son-s1() para-char " + c);
    }
}
