package com.course.common.enums;

import lombok.Getter;

/**
 * 接口返回对象Res枚举类型
 *
 * @author channing
 * @date 2020/3/26 下午2:57
 */
@Getter
public enum ResEnum {
	/** 服务器已成功处理了请求 */
	succ_200("成功"),
	/** 未登录，客户端没有提供认证信息或者认证信息不正确 */
	fail_401("未登录"),
	/** 客户端的权限不足，前台通常无法处理，可以直接提示 */
	fail_403("权限不足"),
	/** 服务器内部错误，无法完成请求 */
	fail_500("服务端错误"),
	/** 服务器内部错误，无法完成请求 */
	fail_801("服务端错误"),
	/** 服务端错误，前台通常无法处理，可以直接提示 */
	exception_901("接口未定义"),
	/** 接口未上线错误，前台通常无法处理，可以直接提示 */
	exception_902("接口未上线"),
	/** 服务器发生异常，前台通常无法处理，可以直接提示 */
	exception_999("服务端异常");

	ResEnum(String msg) {
		this.code = Integer.valueOf(this.name().substring(this.name().indexOf("_") + 1));
		this.msg = msg;
	}

	private Integer code;
	private String msg;

}
