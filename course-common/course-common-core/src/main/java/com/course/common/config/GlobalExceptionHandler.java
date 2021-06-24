package com.course.common.config;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.course.common.constant.CommonConstants;
import com.course.common.entity.Res;
import com.course.common.enums.CommonResponseEnum;
import com.course.common.enums.ServletResponseEnum;
import com.course.common.exception.BaseException;
import com.course.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 * 
 * @author qinlei
 * @date 2021/6/15 下午5:05
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 当前环境
	 */
	@Value("${spring.profiles.active}")
	private String profile;

	/**
	 * 业务异常
	 *
	 * @param e
	 *            异常
	 * @return 异常结果
	 */
	@ExceptionHandler(value = BusinessException.class)
	public Res handleBusinessException(BaseException e) {
		log.error(e.getMessage(), e);
		return Res.fail(e.getMessage());
	}

	/**
	 * 自定义异常
	 *
	 * @param e
	 * @return 异常结果
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public Res handleBaseException(BaseException e) {
		log.error(e.getMessage(), e);
		return Res.fail("");
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
		int code = CommonResponseEnum.SERVER_ERROR.getCode();
		try {
			ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
			code = servletExceptionEnum.getCode();
		} catch (IllegalArgumentException e1) {
			log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
		}
		if (CommonConstants.ENV_PROD.equals(profile)) {
			// 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
			code = CommonResponseEnum.SERVER_ERROR.getCode();
			return Res.fail("");
		}
		return Res.fail(e.getMessage());
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
			int code = CommonResponseEnum.SERVER_ERROR.getCode();
			return Res.fail("");
		}
		return Res.fail("");
	}
}
