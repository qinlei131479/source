package com.course.design.adapter.adaptee;

import com.course.design.adapter.basic.ZGSocket;

/**
 * 适配器模式 主要作用：将一个类的接口转换成另外一个客户希望的接口
 * 
 * @author qinlei
 * @date 2021/6/3 下午12:55
 */
public class ZGSocketGuojiSocketAdapter implements GuoJiSocket {

	/**
	 * 被适配的接口
	 */
	private ZGSocket gbSocket;

	public ZGSocketGuojiSocketAdapter(ZGSocket gbSocket) {
		this.gbSocket = gbSocket;
	}

	@Override
	public void use() {
		gbSocket.use();
	}
}
