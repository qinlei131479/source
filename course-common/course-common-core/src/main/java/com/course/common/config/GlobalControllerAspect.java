package com.course.common.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.course.common.constant.CommonConstants;
import com.course.common.entity.*;
import com.course.common.exception.BaseRuntimeException;
import com.course.common.utils.HuToolUtil;
import com.course.common.utils.RequestUtil;
import com.course.common.utils.ValidUtil;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局参数绑定异常处理、日志记录
 * 
 * @author qinlei
 * @date 2021/6/24 下午4:59
 */
@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class GlobalControllerAspect implements Ordered {
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 2;
	}

	// private final BaseGlobalService baseGlobalService;

	/**
	 * 定义切点Pointcut
	 */
	@Pointcut("execution(* com.course..*.controller.*Controller.*(..))")
	public void doController() {
	}

	@Around("doController()")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = RequestUtil.getRequest();
		if (request == null) {
			// 执行方法
			return point.proceed();
		}
		boolean isInit = false;
		Object api = null;
		// 计算耗时,开始时间
		long beginTime = System.currentTimeMillis();
		String[] path_action = RequestUtil.calApiUrl(request);
		String apiPath = path_action[0];
		Object user = null;// baseGlobalService.getUser(request);
		Object res = null;
		Req req = null;
		Throwable error = null;
		try {
			List<Valid> validList = null;
			// 检查权限
			// Res checkRes = globalService.checkPower(point, request, user,
			// apiPath);
			// api = checkRes.getObj();
			// if (checkRes.checkNotSucc()) {
			// return res = checkRes;
			// }
			// 判断是否需要保存日志
			ValidArg arg = ValidUtil.getArgAndAnnotation.apply(point, RequestBody.class);
			if (arg != null) {
				req = (Req) arg.getArg();
			}
			if (req != null) {
				// 处理校验，初始化状态下，获取validList，非初始化，返回报错信息
				Pg pg = ValidUtil.getArgByClass(point, Pg.class);
				if (pg != null && pg.checkActionStatusInit()) {
					validList = ValidUtil.handleValidList(point, req);
					isInit = true;
				} else {
					BindingResult bindingResult = ValidUtil.getArgByClass(point, BindingResult.class);
					if (bindingResult != null) {
						// 获取验证group数组
						ValidArg validArgGroup = ValidUtil.getArgAndAnnotation.apply(point, Validated.class);
						Class<?>[] ValidatedGroupClazzs = null;
						if (validArgGroup != null) {
							ValidatedGroupClazzs = ((Validated) validArgGroup.getAnnotation()).value();
						}
						for (ObjectError objectError : bindingResult.getAllErrors()) {
							FieldError fieldError = (FieldError) objectError;
							if (ValidUtil.checkValidEnable(req, fieldError.getField(), ValidatedGroupClazzs)) {
								return res = Res.fail(fieldError.getDefaultMessage(), fieldError.getField());
							}
						}
					}
				}
			}
			// 自动填充createUserId，updateUserId
			if (user != null && req != null && isInit == false) {
				Object userId = null;// baseGlobalService.getUserId(request);
				if (userId != null) {
					HuToolUtil.setFieldValueIfExist(req, "createUserId", userId);
					HuToolUtil.setFieldValueIfExist(req, "updateUserId", userId);
				}
			}
			// 执行方法
			res = point.proceed();
			// 设置表单验证列表
			if (CollUtil.isNotEmpty(validList) && Res.class.isInstance(res)) {
				Res<?> resObject = (Res<?>) res;
				String validName = CommonConstants.VALID_LIST;
				if (resObject.getExtMap() != null) {
					if (resObject.getExtMap().get(validName) == null) {
						resObject.getExtMap().put(validName, validList);
					}
				} else {
					resObject.setExtMap(Ext.keys(validName).obj(validList).getExtMap());
				}
			}
		} catch (BaseRuntimeException e) {
			error = e;
			return res =e.getRes() ;
		} catch (Exception e) {
			error = e;
			e.printStackTrace();
			return res = Res.exception("系统异常");
		} finally {
			// 记录日志
			// baseGlobalService.saveLog(api, user, apiPath, req, request, res,
			// error, isInit, beginTime);
		}
		return res;
	}
}
