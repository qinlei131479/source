package com.course.common.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;

import com.course.common.entity.Req;
import com.course.common.entity.Res;

/**
 * 全局Service
 * 
 * @author qinlei
 * @date 2021/6/23 下午12:38
 */
public interface BaseGlobalService<T> {

	/**
	 * 下一个主键
	 */
	Long nextId();

	/**
	 * 获取配置值
	 * 
	 * @param configCode
	 * @return
	 */
	String getConfigValue(String configCode);

	/**
	 * 获取用户
	 *
	 * @param request
	 * @return
	 */
	T getUser(HttpServletRequest request);

	/**
	 * 获取用户id
	 *
	 * @param request
	 * @return
	 */
	Object getUserId(HttpServletRequest request);

	/**
	 * 检查权限
	 *
	 * @param point
	 * @param request
	 * @param user
	 * @param apiPath
	 * @return
	 */
	Res<?> checkPower(ProceedingJoinPoint point, HttpServletRequest request, T user, String apiPath);

	/**
	 * 记录日志
	 *
	 * @param api
	 * @param user
	 * @param apiPath
	 * @param req
	 * @param request
	 * @param res
	 * @param error
	 * @param isInit
	 * @param beginTime
	 */
	void saveLog(Object api, T user, String apiPath, Req req, HttpServletRequest request, Object res, Throwable error,
			boolean isInit, long beginTime);
}
