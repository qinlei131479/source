package com.course.biz.sys.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.course.biz.sys.entity.Api;
import com.course.biz.sys.entity.ApiLog;
import com.course.biz.sys.enums.ApiStatusEnum;
import com.course.biz.sys.enums.PlatformEnum;
import com.course.biz.sys.service.ApiService;
import com.course.biz.sys.service.ConfigService;
import com.course.biz.sys.service.KeyService;
import com.course.common.cache.utils.RedisUtil;
import com.course.common.core.component.InstanceConfig;
import com.course.common.core.config.BaseGlobalService;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.common.core.entity.Valid;
import com.course.common.core.enums.FlagEnum;
import com.course.common.core.enums.RequestAttrEnum;
import com.course.common.core.enums.RequestHeaderEnum;
import com.course.common.core.enums.ResCommonEnum;
import com.course.common.core.utils.HuToolUtil;
import com.course.common.core.utils.RequestUtil;
import com.course.common.core.utils.ValidUtil;
import com.course.common.mybatis.entity.Pg;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
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
	@Override
	public boolean checkActionStatusInit(ProceedingJoinPoint point, Req req, List<Valid> validList) {
		return false;
	}

//	private final KeyService keyService;
//	private final ConfigService configService;
//	private final ApiService apiService;
//	private final RedisUtil redisUtil;
//	private final InstanceConfig instance;
//	/**
//	 * 当前平台
//	 */
//	private static final PlatformEnum PLATFORM = PlatformEnum.back;
//
//	@Override
//	public Long nextId() {
//		// 通过缓存获取id
//		String nextId = this.redisUtil.keyLeftPop();
//		if (StrUtil.isNotBlank(nextId)) {
//			return NumberUtil.parseLong(nextId);
//		}
//		// 如果通过缓存获取失败，通过数据库获取id
//		Long nextIdLong = this.keyService.updateKey();
//		log.warn("通过缓存获取id失败，通过数据库获取，id={}", nextIdLong);
//		return nextIdLong;
//	}
//
//	@Override
//	public String getConfigValue(String code) {
//		return configService.findConfigValueByCode(code);
//	}
//
//	@Override
//	public Object getUser(HttpServletRequest request) {
//		return null;
//	}
//
//	@Override
//	public Object getUserId(HttpServletRequest request) {
//		return null;
//	}
//
//	@Override
//	public Res<?> checkPower(ProceedingJoinPoint point, HttpServletRequest request, Object user, String apiPath) {
//		Api api = this.apiService.findByPlatformAndPath(PLATFORM.getCode(), apiPath);
//		// 接口未定义
//		ResCommonEnum.API_NOT_DEFINE.assertNotNull(api);
//		// 接口未上线
//		if (!ApiStatusEnum.online.getCode().equals(api.getStatus())) {
//			ResCommonEnum.API_NOT_ONLINE.newException(apiPath + "（" + PLATFORM.getName() + "）");
//		}
//		// 接口不需要权限
//		if (FlagEnum.checkNo(api.getNeedPowerFlag())) {
//			return Res.succ(api);
//		}
//		return Res.succ(api);
//	}
//
//	@Override
//	public void saveLog(Object apiObject, Object user, String apiPath, Req req, HttpServletRequest request, Object res,
//			Throwable error, boolean isInit, long beginTime) {
//		// 检查是否需要保存日志
//		if (checkNeedLog(PLATFORM, apiObject, res, error, isInit)) {
//			try {
//				String token = RequestUtil.getHeader_token(request);
//				ApiLog apiLog = buildLog(instance, PlatformEnum.back, apiObject, token, apiPath, req, request, res,
//						error, user);
//				// 登录接口从这里获取token，账号等信息
//				ApiLog requestToken = RequestUtil.getAttr(request, RequestAttrEnum.token);
//				if (requestToken != null) {
//					apiLog.setToken(requestToken.getToken());
//					apiLog.setUserId(requestToken.getUserId());
//					apiLog.setUserName(requestToken.getUserName());
//					apiLog.setUserAccount(requestToken.getUserAccount());
//				}
//				// 放到日志队列中
//				apiLog.setHandleTime(System.currentTimeMillis() - beginTime);
//				this.redisUtil.rightPushApiLogList(JSONUtil.toJsonStr(apiLog));
//			} catch (Exception e) {
//				log.error("日志记录异常:{}", e.getMessage());
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public boolean checkActionStatusInit(ProceedingJoinPoint point, Req req, List<Valid> validList) {
//		Pg pg = ValidUtil.getArgByClass(point, Pg.class);
//		if (pg != null && pg.checkActionStatusInit()) {
//			validList = ValidUtil.handleValidList(point, req);
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 检查是否需要保存日志
//	 *
//	 * @param platform
//	 * @param apiObject
//	 * @param res
//	 * @param error
//	 * @param isInit
//	 * @return
//	 */
//	public boolean checkNeedLog(PlatformEnum platform, Object apiObject, Object res, Throwable error, boolean isInit) {
//		if (error != null) {
//			// 有异常
//			return true;
//		} else if (apiObject != null && apiObject instanceof Api) {
//			Api api = (Api) apiObject;
//			// 非初始化且需要记日志
//			return isInit == false && platform.getCode().equals(api.getPlatform())
//					&& FlagEnum.checkYes(api.getNeedLogFlag());
//		}
//		return false;
//	}
//
//	/**
//	 * 构造日志
//	 *
//	 * @param instance
//	 * @param platform
//	 * @param apiObject
//	 * @param token
//	 * @param apiPath
//	 * @param req
//	 * @param request
//	 * @param res
//	 * @param error
//	 * @return
//	 */
//	public ApiLog buildLog(InstanceConfig instance, PlatformEnum platform, Object apiObject, String token,
//			String apiPath, Req req, HttpServletRequest request, Object res, Throwable error, Object user) {
//		String pageParam = RequestUtil.getHeader(request, RequestHeaderEnum.PAGE_PARAM);
//		ApiLog apiLog = null;
//		// 页面参数初始化日志对象
//		if (StrUtil.isNotBlank(pageParam)) {
//			apiLog = JSONUtil.toBean(URLUtil.decode(pageParam), ApiLog.class);
//		}
//		if (apiLog == null) {
//			apiLog = new ApiLog();
//		}
//		apiLog.setApiPlatform(platform.getCode());
//		if (apiObject != null) {
//			apiLog.setApiId((Long) HuToolUtil.getFieldValueIfExist(apiObject, "id"));
//		}
//		if (user != null) {
//			apiLog.setUserId((Long) HuToolUtil.getFieldValueIfExist(user, "userId"));
//			apiLog.setUserName((String) HuToolUtil.getFieldValueIfExist(user, "userName"));
//			apiLog.setUserAccount((String) HuToolUtil.getFieldValueIfExist(user, "userAccount"));
//		}
//		String reqStr = RequestUtil.getAttr(RequestAttrEnum.bodyString);
//		apiLog.setReq(reqStr);
//		if (res != null) {
//			if (Res.class.isInstance(res)) {
//				apiLog.setRes(JSONUtil.toJsonStr(res));
//				Res<?> resObject = (Res<?>) res;
//				apiLog.setResCode(resObject.getCode());
//				apiLog.setResMsg(resObject.getMsg());
//				apiLog.setRefId((Long) HuToolUtil.getFieldValueIfExist(resObject.getData(), "id"));
//			} else if (String.class.isInstance(res)) {
//				apiLog.setRes((String) res);
//			} else {
//				apiLog.setRes(JSONUtil.toJsonStr(res));
//			}
//		}
//		if (req != null) {
//			if (apiLog.getRefId() == null) {
//				apiLog.setRefId((Long) HuToolUtil.getFieldValueIfExist(req, "id"));
//			}
//		}
//		if (error != null) {
//			apiLog.setError(ExceptionUtil.stacktraceToString(error, 2000));
//		}
//		apiLog.setUri(apiPath);
//		apiLog.setToken(token);
//		apiLog.setOpenid(RequestUtil.getHeader(request, RequestHeaderEnum.WEIXIN_OPENID));
//		apiLog.setClientType(RequestUtil.getHeader(request, RequestHeaderEnum.CLIENT_TYPE));
//		apiLog.setIp(RequestUtil.getIpAddr(request));
//		apiLog.setAgent(request.getHeader(HttpHeaders.USER_AGENT));
//
//		if (request != null) {
//			String url = request.getRequestURL().toString();
//			if (StrUtil.isNotBlank(request.getQueryString())) {
//				url += "?" + request.getQueryString();
//			}
//			apiLog.setUrl(url);
//		}
//		if (instance != null) {
//			// 本地的应用svn版本号为空
//			apiLog.setAppVersion(instance.getVersion());
//			apiLog.setAppName(instance.getAppName());
//		}
//		String feignApiLogStr = RequestUtil.getHeader(request, RequestHeaderEnum.FEIGN_APILOG);
//		ApiLog feignApiLog = JSONUtil.toBean(URLUtil.decode(feignApiLogStr), ApiLog.class);
//		if (feignApiLog != null) {
//			apiLog.setUserId(feignApiLog.getUserId());
//			apiLog.setUserName(feignApiLog.getUserName());
//			apiLog.setUserAccount(feignApiLog.getUserAccount());
//		}
//		apiLog.setFixFlag(FlagEnum.no.getCode());
//		return apiLog;
//	}
}
