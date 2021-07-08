package com.course.common.core.config;

import javax.servlet.http.HttpServletRequest;

import com.course.common.core.entity.Valid;
import org.aspectj.lang.ProceedingJoinPoint;

import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;

import java.util.List;

/**
 * 全局Service
 * 
 * @author qinlei
 * @date 2021/6/23 下午12:38
 */
public interface BaseGlobalService<T> {

	/**
	 * 下一个主键
	 * 
	 * @return
	 */
	default Long nextId() {
		return -1L;
	}

	/**
	 * 获取配置值
	 * 
	 * @param configCode
	 * @return
	 */
	default String getConfigValue(String configCode) {
		return null;
	}

	/**
	 * 获取用户
	 *
	 * @param request
	 * @return
	 */
	default T getUser(HttpServletRequest request) {
		return null;
	}

	/**
	 * 获取用户id
	 *
	 * @param request
	 * @return
	 */
	default Object getUserId(HttpServletRequest request) {
		return null;
	}

	/**
	 * 检查权限
	 *
	 * @param point
	 * @param request
	 * @param user
	 * @param apiPath
	 * @return
	 */
	default Res<?> checkPower(ProceedingJoinPoint point, HttpServletRequest request, T user, String apiPath) {
		return Res.succ();
	}

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
	default void saveLog(Object api, T user, String apiPath, Req req, HttpServletRequest request, Object res,
			Throwable error, boolean isInit, long beginTime) {

	}

	/**
	 * 检查是否初始化方法
	 *
	 * @param point
	 * @return
	 */
	boolean checkActionStatusInit(ProceedingJoinPoint point, Req req, List<Valid> validList);
}
