package com.course.design.decorate;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:16
 */
public class HuaWeiPhone implements PhoneInterface{
    @Override
    public void call() {
        System.out.println("使用华为手机打电话");
    }
}
