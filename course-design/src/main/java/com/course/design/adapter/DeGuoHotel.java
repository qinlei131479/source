package com.course.design.adapter;

import com.course.design.adapter.adaptee.GuoJiSocket;

/**
 * 使用者
 * 
 * @author qinlei
 * @date 2021/6/3 下午12:47
 */
public class DeGuoHotel {

	/**
	 * 国际标准的两眼插座
	 */
	private GuoJiSocket socket;

	public void setGuoJiSocket(GuoJiSocket socket) {
		this.socket = socket;
	}

	/**
	 * 在宾馆使用插座充电
	 */
	public void charge() {
		// 看起来是调用的DBSocket的标准进行充电，但是实质上是通过GBSocket完成充电
		// 通过适配器将GBSocket适配成DBSocket
		socket.use();
	}
}
