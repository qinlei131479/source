package com.course.juc.test;

import java.util.concurrent.Exchanger;

/**
 * 线程之间数据交换
 * 
 * @author qinlei
 * @date 2021/6/16 下午5:11
 */
public class ExchangerTest {

	public static void main(String[] args) {
		Exchanger<CustBook> exchanger = new Exchanger<>();
		// Starting two threads
		new Thread(new ExchangerOne(exchanger)).start();
		new Thread(new ExchangerTwo(exchanger)).start();
	}
}

class CustBook {

	public CustBook(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class ExchangerOne implements Runnable {

	Exchanger<CustBook> ex;

	ExchangerOne(Exchanger<CustBook> ex) {
		this.ex = ex;
	}

	@Override
	public void run() {
		CustBook custBook = new CustBook("book one");
		try {
			CustBook exhangeCustBook = ex.exchange(custBook);
			System.out.println("ExchangerOne: " + exhangeCustBook.getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ExchangerTwo implements Runnable {

	Exchanger<CustBook> ex;

	ExchangerTwo(Exchanger<CustBook> ex) {
		this.ex = ex;
	}

	@Override
	public void run() {
		CustBook custBook = new CustBook("book two");
		try {
			CustBook exhangeCustBook = ex.exchange(custBook);
			System.out.println("ExchangerTwo: " + exhangeCustBook.getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
