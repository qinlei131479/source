package com.course.springtest.resource;

import java.io.InputStream;

/**
 * 封装classpath下xml信息对象
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:23
 */
public class ClasspathResource implements Resource {

	private String location;

	public ClasspathResource(String location) {
		this.location = location;
	}

	@Override
	public InputStream getResource() {
		return this.getClass().getClassLoader().getResourceAsStream(this.location);
	}
}
