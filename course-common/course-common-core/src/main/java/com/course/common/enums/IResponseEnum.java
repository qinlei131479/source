package com.course.common.enums;

/**
 * 统一定义异常接口返回
 * 
 * @author qinlei
 * @date 2021/6/15 下午4:18
 */
public interface IResponseEnum {
	/**
	 * 编号
	 * 
	 * @return
	 */
	Integer getCode();

	/**
	 * msg
	 * 
	 * @return
	 */
	String getMsg();

}
