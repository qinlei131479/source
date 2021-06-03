package com.course.design.build;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:43
 */
public class BuildDemo {

    public static void main(String[] args) {
        ChildBuilder builder = new ChildBuilder();
        // 决定如何创建一个Student
        builder.age(18).name("dili").father("wangjianlin");

        //根据不同的表示去创建对象（私人定制）
        Child child = builder.build();

        System.out.println(child);
    }
}
