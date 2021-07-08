package com.course.sharding.jdbc.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.course.common.core.config.BaseGlobalService;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.common.core.entity.Valid;
import com.course.common.core.utils.ValidUtil;
import com.course.common.mybatis.entity.Pg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 该类主要作用是继承BaseGlobalService，否则调用core模块GlobalControllerAspect#BaseGlobalService将抛出异常
 * 
 * @author qinlei
 * @date 2021/6/28 下午12:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalGlobalServiceImpl implements BaseGlobalService<Object> {
	@Override
	public Long nextId() {
		return null;
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
		return Res.succ();
	}

	@Override
	public void saveLog(Object api, Object user, String apiPath, Req req, HttpServletRequest request, Object res,
			Throwable error, boolean isInit, long beginTime) {

	}

	@Override
	public boolean checkActionStatusInit(ProceedingJoinPoint point, Req req, List<Valid> validList) {
		Pg pg = ValidUtil.getArgByClass(point, Pg.class);
		if (pg != null && pg.checkActionStatusInit()) {
			validList = ValidUtil.handleValidList(point, req);
			return true;
		}
		return false;
	}
}
