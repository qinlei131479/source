package com.course.design.strategy;

import com.course.design.strategy.abs.AirPlanelStrategy;
import com.course.design.strategy.abs.BicycleStrategy;
import com.course.design.strategy.abs.TrainStrategy;
import com.course.design.strategy.abs.TravelStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 环境类(Context)
 *
 * @author qinlei
 * @date 2021/6/3 下午12:27
 */
public class PersonContext {

    /**
     * 拥有一个出行策略引用
     */
    private List<TravelStrategy> strategylist;

    public PersonContext() {
        this.strategylist = new ArrayList<>();
        strategylist.add(new AirPlanelStrategy());
        strategylist.add(new TrainStrategy());
        strategylist.add(new BicycleStrategy());
    }

    public void travel(int type) {
        // 根据具体策略类，执行对应的出行策略
        for (TravelStrategy travelStrategy : strategylist) {
            if (travelStrategy.isOK(type)) {
                travelStrategy.travelWay();
                break;
            }
        }
    }
}
