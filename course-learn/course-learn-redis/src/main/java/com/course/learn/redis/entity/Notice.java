package com.course.learn.redis.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author qinlei
 * @date 2021/7/16 下午10:25
 */
@Data
public class Notice implements Serializable {

	private String name;

	private String val;

	public Notice() {

	}

	public Notice(String name, String val) {
		this.name = name;
		this.val = val;
	}
}
