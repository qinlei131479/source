package com.course.design.decorate;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:19
 */
public class DesignPatternDemo {

    public static void main(String[] args) {
        PhoneInterface phone = new HuaWeiPhone();
        phone.call();
        System.out.println("===========");

        //Phone phone2 = new PhoneDecorate(new IPhone());
        PhoneInterface phone2 = new HuaWeiPhoneDecorate(phone);
        phone2.call();
    }
}
