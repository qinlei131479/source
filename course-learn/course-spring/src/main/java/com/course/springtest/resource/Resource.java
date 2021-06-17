package com.course.springtest.resource;

import java.io.InputStream;

/**
 * 封装XML数据对象
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:23
 */
public interface Resource {

	InputStream getResource();
}
