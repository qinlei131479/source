package com.course.common.entity;

import java.util.Map;

import com.course.common.enums.IResponseEnum;
import com.course.common.enums.ResCommonEnum;
import com.course.common.exception.BaseRuntimeException;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 接口请求参数对象的父类
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
public class Res<T> {

	protected Integer code;
	protected String msg;
	protected String field;
	protected String detail;
	protected T data;
	/**
	 * 扩展对象
	 */
	protected Map<String, Object> extMap;

	public boolean checkSucc() {
		return ResCommonEnum.SUCC.getCode().equals(this.code);
	}

	public boolean checkNotSucc() {
		return ResCommonEnum.SUCC.getCode().equals(this.code) == false;
	}

	/**
	 * 
	 * @param resEnum
	 * @param msg
	 * @param <T>
	 * @return
	 */
	private static <T extends Object> Res<T> build(IResponseEnum resEnum, String msg) {
		return build(resEnum, msg, null);
	}

	/**
	 *
	 * @param resEnum
	 * @param msg
	 * @param obj
	 * @param <T>
	 * @return
	 */
	private static <T extends Object> Res<T> build(IResponseEnum resEnum, String msg, T obj) {
		return build(resEnum, msg, obj, null);
	}

	/**
	 * 
	 * @param resEnum
	 * @param msg
	 * @param obj
	 * @param field
	 * @param <T>
	 * @return
	 */
	private static <T extends Object> Res<T> build(IResponseEnum resEnum, String msg, T obj, String field) {
		Res<T> res = new Res<T>();
		res.setCode(resEnum.getCode());
		res.setMsg(StrUtil.isNotBlank(msg) ? msg : resEnum.getMsg());
		res.setData(obj);
		res.setField(field);
		return res;
	}

	/**
	 * 成功
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> succ() {
		return succ(null);
	}

	/**
	 * 成功 - -返回数据
	 * 
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> succ(T data) {
		return build(ResCommonEnum.SUCC, "", data);
	}

	/**
	 * 成功 - 返回数据+扩展信息
	 * 
	 * @param data
	 * @param ext
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> succ(T data, Ext ext) {
		Res<T> res = succ(data);
		if (ext != null) {
			res.setExtMap(ext.getExtMap());
		}
		return res;
	}

	/**
	 * 失败 - 返回错误信息
	 * 
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> fail(String msg) {
		return fail(msg, null);
	}

	/**
	 * 失败 - 返回错误提示 + 字段
	 * 
	 * @param msg
	 * @param field
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> fail(String msg, String field) {
		return build(ResCommonEnum.FAIL, msg, null, field);
	}

	/**
	 * 异常返回数据
	 *
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> exception() {
		return exception(ResCommonEnum.SERVER_ERROR, null);
	}

	/**
	 * 异常返回数据
	 * 
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> exception(String msg) {
		return exception(ResCommonEnum.SERVER_ERROR, msg);
	}

	/**
	 * 异常返回数据
	 *
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> exception(IResponseEnum responseEnum, String msg) {
		return exception(responseEnum, msg, null);
	}

	/**
	 * 异常返回数据
	 *
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> Res<T> exception(IResponseEnum responseEnum, String msg, Throwable cause) {
		Res<T> res = build(responseEnum, msg);
		res.setDetail(cause != null ? cause.getMessage().substring(50) : null);
		return res;
	}

	/**
	 * 转为BaseRuntimeException对象
	 *
	 * @return
	 */
	public BaseRuntimeException toBaseRuntimeException() {
		return new BaseRuntimeException(this);
	}
}
