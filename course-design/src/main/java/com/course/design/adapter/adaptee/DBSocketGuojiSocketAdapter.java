package com.course.design.adapter.adaptee;

import com.course.design.adapter.basic.DBSocket;

/**
 * 适配器模式
 * 主要作用：将一个类的接口转换成另外一个客户希望的接口
 * 这个类就相当于实际案例中的电源转换头
 * @author qinlei
 * @date 2021/6/3 下午12:55
 */
public class DBSocketGuojiSocketAdapter implements GuoJiSocket{

    //被适配的接口
    private DBSocket dbSocket;

    public DBSocketGuojiSocketAdapter(DBSocket dbSocket) {
        this.dbSocket = dbSocket;
    }

    @Override
    public void use() {
        dbSocket.use();
    }
}
