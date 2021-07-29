package com.course.gateway.filter;

import com.course.common.core.exception.ValidateCodeException;
import com.course.common.core.utils.WebUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.course.common.cache.enums.RedisKeyEnum;
import com.course.common.cache.utils.RedisUtil;
import com.course.common.core.entity.Res;
import com.course.gateway.constant.CommonConstant;
import com.course.gateway.properties.GatewayConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author qinlei
 * @date 2021/7/29 下午4:08
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends AbstractGatewayFilterFactory {

	private final GatewayConfigProperties configProperties;
	private final RedisUtil redisUtil;
	private final ObjectMapper objectMapper;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// 不是登录请求，直接向下执行
			if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), CommonConstant.OAUTH_TOKEN_URL)) {
				return chain.filter(exchange);
			}

			// 刷新token，直接向下执行
			String grantType = request.getQueryParams().getFirst(CommonConstant.GRANT_TYPE);
			if (StrUtil.equals(CommonConstant.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			// 终端设置不校验， 直接向下执行
			try {
				String[] clientInfos = WebUtil.getClientId(request);
				if (configProperties.getIgnoreClients().contains(clientInfos[0])) {
					return chain.filter(exchange);
				}

				// 校验验证码
				// checkCode(request);
			} catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

				final String errMsg = e.getMessage();
				return response.writeWith(Mono.create(monoSink -> {
					try {
						byte[] bytes = objectMapper.writeValueAsBytes(Res.fail(errMsg));
						DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

						monoSink.success(dataBuffer);
					} catch (JsonProcessingException jsonProcessingException) {
						log.error("对象输出异常", jsonProcessingException);
						monoSink.error(jsonProcessingException);
					}
				}));
			}
			return chain.filter(exchange);
		};
	}

	/**
	 * 检查code
	 *
	 * @param request
	 */
	@SneakyThrows
	private void checkCode(ServerHttpRequest request) {
		String code = request.getQueryParams().getFirst("code");

		if (StrUtil.isBlank(code)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		String randomStr = request.getQueryParams().getFirst("randomStr");
		if (StrUtil.isBlank(randomStr)) {
			randomStr = request.getQueryParams().getFirst("mobile");
		}
		String key = RedisKeyEnum.validCode.getKey(randomStr);
		Object codeCache = redisUtil.get(key);
		if (codeCache == null) {
			throw new ValidateCodeException("验证码不合法");
		}
		if (!StrUtil.equals(code, codeCache.toString())) {
			redisUtil.del(key);
			throw new ValidateCodeException("验证码不合法");
		}
		redisUtil.del(key);
	}
}
