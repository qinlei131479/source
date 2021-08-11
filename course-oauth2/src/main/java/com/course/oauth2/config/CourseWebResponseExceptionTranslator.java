package com.course.oauth2.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.course.oauth2.exception.*;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * oauth2异常重写
 * 
 * @author qinlei
 * @date 2021/8/7 下午9:29
 */
@Slf4j
@Component
public class CourseWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	@Override
	@SneakyThrows
	public ResponseEntity translate(Exception e) {
		// 解析异常栈
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
		Exception ase = (AuthenticationException) throwableAnalyzer
				.getFirstThrowableOfType(AuthenticationException.class, causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new CourseAuthenticationException(e.getMessage(), e));
		}
		ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new CourseAccessDeniedException(ase.getMessage(), ase));
		}

		ase = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new CourseInvalidGrantException(ase.getMessage(), ase));
		}

		ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer
				.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new CourseMethodNotSupportedException(ase.getMessage(), ase));
		}

		ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
		if (ase != null) {
			return handleOAuth2Exception((OAuth2Exception) ase);
		}

		return handleOAuth2Exception(
				new CourseServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
	}

	/**
	 * 异常处理
	 * 
	 * @param e
	 * @return
	 */
	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
		int status = e.getHttpErrorCode();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
		headers.set(HttpHeaders.PRAGMA, "no-cache");
		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
			headers.set(HttpHeaders.WWW_AUTHENTICATE,
					String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
		}

		// 客户端异常直接返回客户端,不然无法解析
		if (e instanceof ClientAuthenticationException) {
			return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
		}
		return ResponseEntity.status(e.getHttpErrorCode())
				.body(new CourseOAuth2Exception(e.getMessage(), e.getOAuth2ErrorCode()));
		// return new ResponseEntity<>(new CourseOAuth2Exception(e.getMessage(),
		// e.getOAuth2ErrorCode()));

	}
}
