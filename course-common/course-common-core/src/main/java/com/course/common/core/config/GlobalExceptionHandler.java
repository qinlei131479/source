package com.course.common.core.config;

import com.course.common.core.constant.CommonConstants;
import com.course.common.core.entity.Res;
import com.course.common.core.enums.ResCommonEnum;
import com.course.common.core.exception.BaseRuntimeException;
import com.course.common.core.exception.BusinessException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 * 
 * @author qinlei
 * @date 2021/6/15 下午5:05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 当前环境
	 */
	@Value("${spring.profiles.active:dev}")
	private String profile;

	/**
	 * 自定义异常
	 *
	 * @param e
	 * @return 异常结果
	 */
	@ExceptionHandler({ BaseRuntimeException.class, BusinessException.class })
	public Res handleBaseException(BaseRuntimeException e) {
		log.error(e.getMessage(), e);
		return e.getRes();
	}

	/**
	 * Controller上一层相关异常
	 *
	 * @param e
	 *            异常
	 * @return 异常结果
	 */
	@ExceptionHandler({ NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class, MissingPathVariableException.class,
			MissingServletRequestParameterException.class, TypeMismatchException.class,
			HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
			HttpMediaTypeNotAcceptableException.class, ServletRequestBindingException.class,
			ConversionNotSupportedException.class, MissingServletRequestPartException.class,
			AsyncRequestTimeoutException.class })
	public Res handleServletException(Exception e) {
		if (CommonConstants.ENV_PROD.equals(profile)) {
			// 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
			return Res.exception();
		}
		return Res.exception(ResCommonEnum.SERVER_ERROR, e.getMessage());
	}

	/**
	 * 未定义异常
	 *
	 * @param e
	 * @return 异常结果
	 */
	@ExceptionHandler(value = Exception.class)
	public Res handleException(Exception e) {
		log.error(e.getMessage(), e);
		// 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
		if (CommonConstants.ENV_PROD.equals(profile)) {
			return Res.exception();
		}
		return Res.exception(ResCommonEnum.SERVER_ERROR, e.getMessage());
	}
}
