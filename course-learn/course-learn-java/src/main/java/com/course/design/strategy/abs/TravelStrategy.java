package com.course.design.strategy.abs;

/**
 * 抽象策略类
 * @author qinlei
 * @date 2021/6/3 下午12:29
 */
public interface TravelStrategy {

    //出行方式
    void travelWay();

    boolean isOK(int type);
}
