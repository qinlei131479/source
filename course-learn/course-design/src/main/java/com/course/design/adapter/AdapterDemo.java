package com.course.design.adapter;

import com.course.design.adapter.adaptee.DBSocketGuojiSocketAdapter;
import com.course.design.adapter.adaptee.ZGSocketGuojiSocketAdapter;
import com.course.design.adapter.basic.DBSocketImpl;
import com.course.design.adapter.basic.ZGSocketImpl;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:47
 */
public class AdapterDemo {

	public static void main(String[] args) {
		DeGuoHotel hotel = new DeGuoHotel();
		hotel.setGuoJiSocket(new DBSocketGuojiSocketAdapter(new DBSocketImpl()));
		hotel.charge();
		hotel.setGuoJiSocket(new ZGSocketGuojiSocketAdapter(new ZGSocketImpl()));
		hotel.charge();
	}
}
