package com.course.jvm.test.model;

/**
 * @author qinlei
 * @date 2021/6/12 下午10:01
 */
public class Father {

    // 被调用的父类 class Father {
    public void f1() {
        System.out.println("father-f1()");
    }

    public void f1(int i) {
        System.out.println("father-f1() para-int " + i);
    }
}
