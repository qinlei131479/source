package com.course.springboot.remote;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.course.common.config.BaseGlobalService;
import com.course.common.entity.Req;
import com.course.common.entity.Res;
import com.course.springboot.service.KeyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局服务service
 * 
 * @author qinlei
 * @date 2021/6/25 下午5:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalGlobalServiceImpl implements BaseGlobalService<Object> {

	private final KeyService keyService;

	@Override
	public Long nextId() {
		// 如果通过缓存获取失败，通过数据库获取id
		Long nextIdLong = this.keyService.updateKey();
		log.warn("通过缓存获取id失败，通过数据库获取，id={}", nextIdLong);
		return nextIdLong;
	}

	@Override
	public String getConfigValue(String configCode) {
		return null;
	}

	@Override
	public Object getUser(HttpServletRequest request) {
		return null;
	}

	@Override
	public Object getUserId(HttpServletRequest request) {
		return null;
	}

	@Override
	public Res<?> checkPower(ProceedingJoinPoint point, HttpServletRequest request, Object user, String apiPath) {
		return null;
	}

	@Override
	public void saveLog(Object api, Object user, String apiPath, Req req, HttpServletRequest request, Object res,
			Throwable error, boolean isInit, long beginTime) {

	}
}
