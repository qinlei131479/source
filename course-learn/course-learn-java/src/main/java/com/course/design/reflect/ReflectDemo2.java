package com.course.design.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 越过泛型检查 (泛型，主要是作用在编译时)
 * 让ArrayList<Integer>中可以存储String类型数据
 *
 * @author qinlei
 * @date 2021/6/3 下午3:12
 */
public class ReflectDemo2 {

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1100);
        //编译时，会进行泛型检查，不允许添加String类型
//        list.add("");

        //利用反射解决这个问题
        //2：再去通过反射获取ArrayList<Integer>的add方法的Method对象
        Class c1 = list.getClass();
        Method method = c1.getMethod("add",Object.class);

        //3：调用Method对象的invoke方法
        method.invoke(list, "张三");
        method.invoke(list, 1);
        method.invoke(list, 10f);

        System.out.println(list);
        System.out.println("=================================");

        //想去修改Student类的私有属性值
        Student student = new Student();
        System.out.println(student);
        //调用工具类修改私有变量的值
        ReflectTools.setProperty(student, "name", "张三");
        ReflectTools.setProperty(student, "age", 25);

        System.out.println(student);
    }
}
