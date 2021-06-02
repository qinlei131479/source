package com.course.design.template;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:21
 */
public class TemplateTest {

    public static void main(String[] args) {
        GetTimeTemplate time = new ForTemplate();
        System.out.println("耗时 "+time.getTime()+" 毫秒");

        GetTimeTemplate time2 = new CopyFileTemplate();
        System.out.println("耗时 "+time2.getTime()+" 毫秒");
    }
}
